package parking_lot.model.base;

import parking_lot.enums.VehicleType;

public abstract class Vehicle {
    private final VehicleType vehicleType;
    private final String registrationNumber;
    private final String color;

    public Vehicle(String registrationNumber, String color, VehicleType vehicleType) {
        this.registrationNumber = registrationNumber;
        this.color = color;
        this.vehicleType = vehicleType;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getColor() {
        return color;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
}
