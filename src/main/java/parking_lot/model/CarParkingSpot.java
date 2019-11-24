package parking_lot.model;

import parking_lot.enums.VehicleType;
import parking_lot.model.base.ParkingSpot;

public class CarParkingSpot extends ParkingSpot {
    public CarParkingSpot(int spotId) {
        super(spotId, VehicleType.CAR);
    }
}
