package parking_lot.model.base;

import parking_lot.constants.Constant;
import parking_lot.enums.ParkingSpotStatusType;
import parking_lot.enums.ResponseStatus;
import parking_lot.enums.VehicleType;
import parking_lot.exception.ParkingLotException;

public abstract class ParkingSpot implements Spot {

    private final int spotId;
    private final VehicleType vehicleType;
    private ParkingSpotStatusType parkingSpotStatusType;
    private Vehicle vehicle;

    public ParkingSpot(int spotId, VehicleType vehicleType) {
        this.spotId = spotId;
        this.vehicleType = vehicleType;
        setEmpty();
    }

    @Override
    public int getSpotId() {
        return spotId;
    }

    @Override
    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public boolean isOccupied() {
        return parkingSpotStatusType == ParkingSpotStatusType.OCCUPIED;
    }

    @Override
    public void assignVehicle(Vehicle vehicle) throws ParkingLotException {
        if (isOccupied())
            throw new ParkingLotException(ResponseStatus.BAD_REQUEST, Constant.PARKING_SPOT_FULL);
        if (this.vehicleType != vehicle.getVehicleType())
            throw new ParkingLotException(ResponseStatus.BAD_REQUEST, Constant.PARKING_SPOT_AND_VEHICLE_TYPE_MIS_MATCH);
        this.vehicle = vehicle;
        setOccupied();
    }

    @Override
    public void removeVehicle(int spotId) throws ParkingLotException {

    }

    @Override
    public ParkingSpotStatusType getParkingSpotStatusType() {
        return parkingSpotStatusType;
    }

    private void setOccupied() {
        this.parkingSpotStatusType = ParkingSpotStatusType.OCCUPIED;
    }

    private void setEmpty() {
        this.parkingSpotStatusType = ParkingSpotStatusType.EMPTY;
    }
}
