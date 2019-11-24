package parking_lot.model;

import parking_lot.enums.VehicleType;
import parking_lot.model.base.Vehicle;

public class Car extends Vehicle {
    public Car(String registrationNumber, String color) {
        super(registrationNumber, color, VehicleType.CAR);
    }
}
