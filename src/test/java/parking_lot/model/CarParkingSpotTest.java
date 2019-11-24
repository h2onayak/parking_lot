package parking_lot.model;

import org.junit.jupiter.api.*;
import parking_lot.enums.ParkingSpotStatusType;
import parking_lot.enums.VehicleType;
import parking_lot.exception.ParkingLotException;
import parking_lot.model.base.ParkingSpot;
import parking_lot.model.base.Spot;
import parking_lot.model.base.Vehicle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class CarParkingSpotTest {
    //This are the tests for assignVehicle() only
    @DisplayName("Assign vehicle when")
    @Nested
    class AssignVehicleTests {

        private Spot spot;

        @BeforeEach
        void beforeEach() {
            spot = spy(new CarParkingSpot(1));
        }

        @DisplayName("parking spot is occupied")
        @Test
        void testWhenSpotsAreFull() {
            Vehicle vehicle = new Car("KA 102", "white");
            when(spot.isOccupied()).thenReturn(true);
            assertThrows(ParkingLotException.class, () -> spot.assignVehicle(vehicle));
        }

        @DisplayName("mismatch in the vehicle type")
        @Test
        void testWhenThereIsIncorrectVehicleType() {
            Vehicle vehicle = spy(new Car("KA 102", "white"));
            when(vehicle.getVehicleType()).thenReturn(VehicleType.BIKE);
            assertThrows(ParkingLotException.class, () -> spot.assignVehicle(vehicle));
        }

        @DisplayName("vehicle type is correct")
        @Test
        void testWhenThereIsCorrectVehicleGiven() throws ParkingLotException {
            Vehicle vehicle = new Car("KA 101112", "black");
            when(spot.isOccupied()).thenReturn(false);
            assertDoesNotThrow(() -> spot.assignVehicle(vehicle));
            spot.assignVehicle(vehicle);
            assertEquals(ParkingSpotStatusType.OCCUPIED, spot.getParkingSpotStatusType());
        }
    }

    //This are the tests for removeVehicle() only
    @DisplayName("Remove vehicle when")
    @Nested
    class RemoveVehicleTests {

        private ParkingSpot spot;

        @BeforeEach
        void beforeEach() {
            spot = spy(new CarParkingSpot(5));
        }

        @DisplayName("spotId is incorrect")
        @Test
        void testWhenInCorrectSpotIdIsGiven() {

            assertThrows(ParkingLotException.class, () -> spot.removeVehicle(1));
        }

        @DisplayName("spotId is correct")
        @Test
        void testWhenCorrectSpotIdIsGiven() throws ParkingLotException {
            when(spot.isVehicleExistOnSlot(any(Vehicle.class))).thenReturn(true);

            assertDoesNotThrow(() -> spot.removeVehicle(5));
            spot.removeVehicle(5);
            Assertions.assertEquals(ParkingSpotStatusType.EMPTY, spot.getParkingSpotStatusType());
        }

        @DisplayName("spotId is correct but vehicle does not exist on spot")
        @Test
        void testWhenCorrectSpotIdButVehicleDoesNotExist() {
            when(spot.isVehicleExistOnSlot(any(Vehicle.class))).thenReturn(false);

            assertThrows(ParkingLotException.class, () -> spot.removeVehicle(5));
        }

    }
}