package parking_lot.service;

import parking_lot.constants.Constant;
import parking_lot.enums.ResponseStatus;
import parking_lot.exception.ParkingLotException;
import parking_lot.model.CarParkingSpot;
import parking_lot.model.base.Spot;
import parking_lot.model.base.Vehicle;
import parking_lot.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;

public class ParkingLotServiceImpl implements ParkingLotService {

    private static ParkingLotServiceImpl parkingLotServiceInstance = null;
    private static Object mutex = new Object();
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
        return null;
    }

    @Override
    public Response getParkingLotStatus() throws ParkingLotException {
        return null;
    }

    @Override
    public Response getRegistrationNumbersForColor(String color) throws ParkingLotException {
        return null;
    }

    @Override
    public Response getParkedSpotIdForRegistrationNumber(String registrationNumber) throws ParkingLotException {
        return null;
    }

    @Override
    public Response getParkedSpotIdsForColorOfVehicle(String color) throws ParkingLotException {
        return null;
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

    private void verifyParkingSpotsCreated() throws ParkingLotException {
        if (parkingSpotsMap == null || getParkingLotSize() <= 0)
            throw new ParkingLotException(ResponseStatus.NOT_FOUND, Constant.NO_PARKING_SPOTS_CREATED);
    }

    private Integer getFreeSpotToPark() {
        return availableSpots != null ? availableSpots.poll() : null;
    }
}
