package parking_lot.model;

import parking_lot.enums.ParkingSpotStatusType;
import parking_lot.exception.ParkingLotException;

public interface Spot {
    int getSpotId();

    Vehicle getVehicle();

    boolean isOccupied();

    void assignVehicle(Vehicle vehicle) throws ParkingLotException;

    void removeVehicle(int spotId) throws ParkingLotException;

    ParkingSpotStatusType getParkingSpotStatusType();
}
