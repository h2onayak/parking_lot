package parking_lot.service;

import parking_lot.exception.ParkingLotException;
import parking_lot.model.base.Vehicle;
import parking_lot.response.Response;

public class ParkingLotServiceImpl implements ParkingLotService {

    private static ParkingLotServiceImpl parkingLotServiceInstance = null;
    private static Object mutex = new Object();

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
    public Response createParkingLot(int numberOfSpots) throws ParkingLotException {
        return null;
    }

    @Override
    public Response park(Vehicle vehicle) throws ParkingLotException {
        return null;
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
}
