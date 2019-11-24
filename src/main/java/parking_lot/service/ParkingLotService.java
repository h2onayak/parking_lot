package parking_lot.service;

import parking_lot.exception.ParkingLotException;
import parking_lot.model.base.Vehicle;
import parking_lot.response.Response;

public interface ParkingLotService {

    static ParkingLotService getInstance() {
        return ParkingLotServiceImpl.getParkingLotServiceInstance();
    }
    //Only for testing purpose
    int getParkingLotSize();

    Response createParkingLot(int numberOfSpots) throws ParkingLotException;

    Response park(Vehicle vehicle) throws ParkingLotException;

    Response exit(int spotNumber) throws ParkingLotException;

    Response getParkingLotStatus() throws ParkingLotException;

    Response getRegistrationNumbersForColor(String color) throws ParkingLotException;

    Response getParkedSpotIdForRegistrationNumber(String registrationNumber) throws ParkingLotException;

    Response getParkedSpotIdsForColourOfVehicle(String color) throws ParkingLotException;
}
