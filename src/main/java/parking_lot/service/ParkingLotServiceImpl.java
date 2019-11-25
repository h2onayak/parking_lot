package parking_lot.service;

import parking_lot.constants.Constant;
import parking_lot.enums.ResponseStatus;
import parking_lot.exception.ParkingLotException;
import parking_lot.model.CarParkingSpot;
import parking_lot.model.base.Spot;
import parking_lot.model.base.Vehicle;
import parking_lot.response.Response;

import java.util.*;
import java.util.stream.Collectors;

import static parking_lot.constants.Constant.PARKING_SPOT_NOT_EXIST;

public class ParkingLotServiceImpl implements ParkingLotService {

    private static ParkingLotServiceImpl parkingLotServiceInstance = null;
    private static final Object mutex = new Object();
    private int parkingLotSize;
    //To give spots from entry point of parking lot.
    private PriorityQueue<Integer> availableSpots;
    private Map<Integer, Spot> parkingSpotsMap;

    private ParkingLotServiceImpl() {
    }

    static ParkingLotService getParkingLotServiceInstance() {
        if (parkingLotServiceInstance == null) {
            synchronized (mutex) {
                if (parkingLotServiceInstance == null) {
                    parkingLotServiceInstance = new ParkingLotServiceImpl();
                }
            }
        }
        return parkingLotServiceInstance;
    }

    @Override
    public int getParkingLotSize() {
        return parkingLotSize;
    }

    @Override
    public Response createParkingLot(int numberOfSpots) throws ParkingLotException {
        if (getParkingLotSize() > 0)
            throw new ParkingLotException(Constant.PARKING_LOT_EXIST);
        if (numberOfSpots <= 0)
            throw new ParkingLotException(Constant.INVALID_INPUT);
        this.parkingLotSize = numberOfSpots;
        initializeParkingSpots();
        return new Response(ResponseStatus.CREATED, String.format(Constant.SLOT_CREATED_MESSAGE, numberOfSpots));
    }

    private void initializeParkingSpots() {
        parkingSpotsMap = new HashMap<>();
        availableSpots = new PriorityQueue<>(this.parkingLotSize);
        //Currently, all the parking spots will be supporting to park Car type vehicles.
        for (int i = 1; i <= this.parkingLotSize; i++) {
            parkingSpotsMap.put(i, new CarParkingSpot(i));
            availableSpots.offer(i);
        }
    }

    @Override
    public Response park(Vehicle vehicle) throws ParkingLotException {
        verifyParkingSpotsCreated();
        if (vehicle == null)
            throw new ParkingLotException(Constant.INVALID_INPUT);
        Integer spotId = getFreeSpotToPark();
        if (Objects.isNull(spotId))
            throw new ParkingLotException(Constant.PARKING_IS_FULL);
        if (isDuplicateRegistrationPark(vehicle.getRegistrationNumber()))
            throw new ParkingLotException(Constant.DUPLICATE_REGISTRATION_NUMBER);
        Spot spot = parkingSpotsMap.get(spotId);
        spot.assignVehicle(vehicle);
        return new Response(ResponseStatus.OK, String.format(Constant.ALLOCATED_SLOT_MESSAGE, spotId));
    }

    @Override
    public Response exit(int spotNumber) throws ParkingLotException {
        verifyParkingSpotsCreated();
        Spot spot = parkingSpotsMap.get(spotNumber);
        if (Objects.isNull(spot))
            throw new ParkingLotException(ResponseStatus.NOT_FOUND, PARKING_SPOT_NOT_EXIST);
        spot.removeVehicle(spotNumber);
        setSpotFreeToPark(spot.getSpotId());
        return new Response(ResponseStatus.OK, String.format(Constant.LEAVE_SLOT_MESSAGE, spot.getSpotId()));
    }

    @Override
    public Response getParkingLotStatus() throws ParkingLotException {
        verifyParkingSpotsCreated();
        Set<Spot> spots = parkingSpotsMap.values()
                .stream()
                .filter(Spot::isOccupied)
                .sorted(Comparator.comparingInt(Spot::getSpotId))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (spots.isEmpty())
            throw new ParkingLotException(ResponseStatus.NOT_FOUND, Constant.NO_VEHICLE_STATUS);
        StringBuilder br = new StringBuilder();
        br.append(Constant.STATUS_TEMPLATE);
        for (Spot spot : spots) {
            if (Objects.nonNull(spot.getVehicle())) {
                br.append("\n").append(spot.getSpotId()).append("\t\t").append(spot.getVehicle().getRegistrationNumber()).append("\t\t").append(spot.getVehicle().getColor());
            }
        }
        return new Response(ResponseStatus.OK, br.toString());
    }

    @Override
    public Response getRegistrationNumbersForColor(String color) throws ParkingLotException {
        verifyParkingSpotsCreated();
        Set<String> registrationNumbers = parkingSpotsMap.values()
                .stream()
                .filter(spot -> spot.isOccupied() && spot.getVehicle().getColor().equalsIgnoreCase(color))
                .map(spot -> spot.getVehicle().getRegistrationNumber())
                .collect(Collectors.toSet());
        if (registrationNumbers.isEmpty())
            throw new ParkingLotException(ResponseStatus.NOT_FOUND, Constant.NO_VEHICLE_PARKED_ON_COLOR);
        return new Response(ResponseStatus.OK, registrationNumbers.toString().replace("[", "").replace("]", ""));
    }

    @Override
    public Response getParkedSpotIdForRegistrationNumber(String registrationNumber) throws ParkingLotException {
        verifyParkingSpotsCreated();
        Spot spot = getParkingSpotForRegistrationNumber(registrationNumber);
        if (Objects.isNull(spot))
            throw new ParkingLotException(ResponseStatus.NOT_FOUND, Constant.VEHICLE_NOT_FOUND);
        return new Response(ResponseStatus.OK, String.valueOf(spot.getSpotId()));
    }

    @Override
    public Response getParkedSpotIdsForColourOfVehicle(String color) throws ParkingLotException {
        verifyParkingSpotsCreated();
        Set<Integer> parkingSpotIds = parkingSpotsMap.values()
                .stream()
                .filter(spot -> spot.isOccupied() && spot.getVehicle().getColor().equalsIgnoreCase(color))
                .map(Spot::getSpotId)
                .collect(Collectors.toSet());
        if (parkingSpotIds.isEmpty())
            throw new ParkingLotException(ResponseStatus.NOT_FOUND, Constant.NO_VEHICLE_PARKED_ON_COLOR);
        return new Response(ResponseStatus.OK, parkingSpotIds.toString().replace("[", "").replace("]", ""));
    }

    private Spot getParkingSpotForRegistrationNumber(String registrationNumber) {
        return parkingSpotsMap.values()
                .stream()
                .filter(spot -> spot.isOccupied() && spot.getVehicle().getRegistrationNumber().equalsIgnoreCase(registrationNumber))
                .findFirst()
                .orElse(null);
    }

    private boolean isDuplicateRegistrationPark(String registrationNumber) {
        Spot spot = getParkingSpotForRegistrationNumber(registrationNumber);
        return Objects.nonNull(spot);
    }

    private void setSpotFreeToPark(int spotId) {
        availableSpots.offer(spotId);
    }

    private void verifyParkingSpotsCreated() throws ParkingLotException {
        if (parkingSpotsMap == null || getParkingLotSize() <= 0)
            throw new ParkingLotException(ResponseStatus.NOT_FOUND, Constant.NO_PARKING_SPOTS_CREATED);
    }

    private Integer getFreeSpotToPark() {
        return availableSpots != null ? availableSpots.poll() : null;
    }
}
