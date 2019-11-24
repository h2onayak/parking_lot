package parking_lot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import parking_lot.enums.VehicleType;
import parking_lot.exception.ParkingLotException;
import parking_lot.model.Car;
import parking_lot.model.base.Vehicle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingLotServiceImplTest {

    @DisplayName("Create parking spots when")
    @Nested
    class CreatingParkingLotTests {
        ParkingLotService parkingLotService;

        @BeforeEach
        void setUp() {
            parkingLotService = spy(ParkingLotService.getInstance());
        }

        @DisplayName("number of spots are zero")
        @Test
        void testZeroParkingLotSize() {
            assertThrows(ParkingLotException.class, () -> parkingLotService.createParkingLot(0));
        }

        @DisplayName("number of spots are negative")
        @Test
        void testNegativeParkingLotSize() {
            assertThrows(ParkingLotException.class, () -> parkingLotService.createParkingLot(-1));
        }

        @DisplayName("number of spots are positive")
        @Test
        void testPositiveParkingLotSize() throws ParkingLotException {
            int numberOfSpots = 5;
            assertDoesNotThrow(() -> parkingLotService.createParkingLot(5));
            assertEquals(numberOfSpots, parkingLotService.getParkingLotSize());
        }

        @DisplayName("parking spots are already exist")
        @Test
        void testWhenSpotsAreAlreadyCreated() {
            when(parkingLotService.getParkingLotSize()).thenReturn(1);
            assertThrows(ParkingLotException.class, () -> parkingLotService.createParkingLot(2));
        }
    }

    @DisplayName("Park vehicle when")
    @Nested
    class ParkVehicleTests {
        ParkingLotService parkingLotService;

        @BeforeEach
        void setUp() {
            parkingLotService = spy(ParkingLotService.getInstance());
        }

        @DisplayName("no parking spots created")
        @Test
        void testParkingVehicleWhenNoSpotsCreated() {
            Vehicle vehicle = spy(new Car("KA 102", "white"));
            when(parkingLotService.getParkingLotSize()).thenReturn(0);
            assertThrows(ParkingLotException.class, () -> parkingLotService.park(vehicle));
        }

        @DisplayName("parking vehicle null")
        @Test
        void testParkingVehicleIsNull() {
            assertThrows(ParkingLotException.class, () -> parkingLotService.park(null));
        }

        @DisplayName("mismatch in the vehicle type")
        @Test
        void testParkingVehicleTypeNotMatching() {
            Vehicle vehicle = spy(new Car("KA 102", "white"));
            when(vehicle.getVehicleType()).thenReturn(VehicleType.BIKE);
            assertThrows(ParkingLotException.class, () -> parkingLotService.park(vehicle));
        }

        @DisplayName("parking duplicate registration number")
        @Test
        void testWhenSameVehicleRegistrationNumberIsAlreadyParked() {
            Vehicle vehicle = spy(new Car("KA 102", "white"));
            assertDoesNotThrow(() -> parkingLotService.createParkingLot(2));
            assertDoesNotThrow(() -> parkingLotService.park(vehicle));
            assertThrows(ParkingLotException.class, () -> parkingLotService.park(vehicle));
        }

        @DisplayName("parking is full")
        @Test
        void testWhenParkingIsFull() {
            Vehicle vehicle1 = spy(new Car("KA 102", "white"));
            Vehicle vehicle2 = spy(new Car("KA 103", "Black"));
            assertDoesNotThrow(() -> parkingLotService.createParkingLot(1));
            assertDoesNotThrow(() -> parkingLotService.park(vehicle1));
            assertThrows(ParkingLotException.class, () -> parkingLotService.park(vehicle2));
        }

        @DisplayName("parking space is present and vehicle is valid")
        @Test
        void testWhenParkingExistAndVehicleIsValid() {
            Vehicle vehicle = spy(new Car("KA 104", "white"));
            assertDoesNotThrow(() -> parkingLotService.createParkingLot(1));
            assertDoesNotThrow(() -> parkingLotService.park(vehicle));
        }
    }

    @DisplayName("Exit vehicle when")
    @Nested
    class ExitVehicleTests {
        ParkingLotService parkingLotService;

        @BeforeEach
        void setUp() {
            parkingLotService = spy(ParkingLotService.getInstance());
        }

        @DisplayName("no parking spots created")
        @Test
        void testExitWhenNoSpotsCreated() {
            assertThrows(ParkingLotException.class, () -> parkingLotService.exit(1));
        }

        @DisplayName("spot id does not exist")
        @Test
        void testWhenParkingSpotIdNotExist() {
            assertDoesNotThrow(() -> parkingLotService.createParkingLot(1));
            assertThrows(ParkingLotException.class, () -> parkingLotService.exit(2));
        }

        @DisplayName("no vehicle was parked on spot id")
        @Test
        void testWhenNoVehicleParkedOnSpotId() {
            assertDoesNotThrow(() -> parkingLotService.createParkingLot(1));
            assertThrows(ParkingLotException.class, () -> parkingLotService.exit(1));
        }

        @DisplayName("spot id is correct")
        @Test
        void testWhenThereIsCorrectSpotIdGiven() {
            Vehicle vehicle = spy(new Car("KA 104", "white"));
            assertDoesNotThrow(() -> parkingLotService.createParkingLot(2));
            assertDoesNotThrow(() -> parkingLotService.park(vehicle));
            assertDoesNotThrow(() -> parkingLotService.exit(1));
        }
    }

    @DisplayName("Parking lot status when")
    @Nested
    class ParkingLotStatusTests {
        ParkingLotService parkingLotService;

        @BeforeEach
        void setUp() {
            parkingLotService = spy(ParkingLotService.getInstance());
        }

        @DisplayName("no spots are created")
        @Test
        void testParkingLotStatusWhenNoSpotsCreated() {
            assertDoesNotThrow(()->parkingLotService.createParkingLot(3));
            when(parkingLotService.getParkingLotSize()).thenReturn(0);
            assertThrows(ParkingLotException.class,()->parkingLotService.getParkingLotStatus());
        }

        @DisplayName("spots are empty")
        @Test
        void testParkingLotStatusWhenSpotsAreEmpty(){
            assertDoesNotThrow(()->parkingLotService.createParkingLot(3));
            assertThrows(ParkingLotException.class,()->parkingLotService.getParkingLotStatus());
        }

        @DisplayName("few spots are parked")
        @Test
        void testParkingLotStatusWhenNotFullyParked() throws ParkingLotException {
            Vehicle vehicle = spy(new Car("CAR05", "Red"));
            assertDoesNotThrow(()->parkingLotService.createParkingLot(2));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertDoesNotThrow(()->parkingLotService.getParkingLotStatus());
        }

        @DisplayName("all spots are parked")
        @Test
        void testParkingLotStatusWhenFullyParked() {
            Vehicle vehicle1 = spy(new Car("CAR11", "White"));
            Vehicle vehicle2 = spy(new Car("CAR12", "Black"));
            assertDoesNotThrow(()->parkingLotService.createParkingLot(2));
            assertDoesNotThrow(()->parkingLotService.park(vehicle1));
            assertDoesNotThrow(()->parkingLotService.park(vehicle2));
            assertDoesNotThrow(()->parkingLotService.getParkingLotStatus());
        }
    }

    @DisplayName("Get registration numbers for mentioned color when")
    @Nested
    class RegistrationNumbersOnColourTests {
        ParkingLotService parkingLotService;

        @BeforeEach
        void setUp() {
            parkingLotService = spy(ParkingLotService.getInstance());
        }

        @DisplayName("no spots created")
        @Test
        void testGetRegistrationNumbersOnColorForNoSpotsCreated() {
            assertDoesNotThrow(()->parkingLotService.createParkingLot(3));
            when(parkingLotService.getParkingLotSize()).thenReturn(0);
            assertThrows(ParkingLotException.class,()->parkingLotService.getRegistrationNumbersForColor("White"));
        }

        @DisplayName("all spots are empty")
        @Test
        void testGetRegistrationNumbersOnColorForEmptyLot() {
            assertDoesNotThrow(()->parkingLotService.createParkingLot(3));
            assertThrows(ParkingLotException.class,()->parkingLotService.getRegistrationNumbersForColor("Silver"));
        }

        @DisplayName("no colour match found")
        @Test
        void testGetRegistrationNumbersOnNoColorMatch() {
            Vehicle vehicle = spy(new Car("KA01KA02","maroon"));
            assertDoesNotThrow(()->parkingLotService.createParkingLot(3));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertThrows(ParkingLotException.class,()->parkingLotService.getRegistrationNumbersForColor("Red"));
        }

        @DisplayName("correct color match found")
        @Test
        void testGetRegistrationNumbersOnColorMatch() {
            Vehicle vehicle = spy(new Car("KA01KA03","Green"));
            assertDoesNotThrow(()->parkingLotService.createParkingLot(3));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertDoesNotThrow(()->parkingLotService.getRegistrationNumbersForColor(vehicle.getColor()));
        }
    }

    @DisplayName("Parked spot id on registration number when")
    @Nested
    class ParkedSpotIdOnRegistrationNumberTests {
        ParkingLotService parkingLotService;

        @BeforeEach
        void setUp() {
            parkingLotService = spy(ParkingLotService.getInstance());
        }

        @DisplayName("no spots created")
        @Test
        void testGetSpotIdOfRegistrationNumberWhenNoSpotsCreated() {
            assertDoesNotThrow(()->parkingLotService.createParkingLot(3));
            when(parkingLotService.getParkingLotSize()).thenReturn(0);
            assertThrows(ParkingLotException.class,()->parkingLotService.getParkedSpotIdForRegistrationNumber("REG01"));
        }

        @DisplayName("all spots are empty")
        @Test
        void testGetSpotIdOfRegistrationNumberWhenSpotsAreEmpty() {
            assertDoesNotThrow(()->parkingLotService.createParkingLot(3));
            assertThrows(ParkingLotException.class,()->parkingLotService
                    .getParkedSpotIdForRegistrationNumber("REG02"));
        }

        @DisplayName("no registration number match found")
        @Test
        void testGetSpotIdOfRegistrationNumberWhenNoRegistrationMatch() {
            Vehicle vehicle = spy(new Car("REG02","Blue"));
            assertDoesNotThrow(()->parkingLotService.createParkingLot(4));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertThrows(ParkingLotException.class,()->parkingLotService
                    .getParkedSpotIdForRegistrationNumber("REG05"));
        }

        @DisplayName("correct registration number match found")
        @Test
        void testGetSpotIdOfRegistrationNumberWhenRegistrationMatched() {
            Vehicle vehicle = spy(new Car("REG02","Blue"));
            assertDoesNotThrow(()->parkingLotService.createParkingLot(4));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertDoesNotThrow(()->parkingLotService
                    .getParkedSpotIdForRegistrationNumber(vehicle.getRegistrationNumber()));
        }
    }

    @DisplayName("Parked spots id's on colour match when")
    @Nested
    class ParkedSpotIdsOnColourMatchTests {
        ParkingLotService parkingLotService;

        @BeforeEach
        void setUp() {
            parkingLotService = spy(ParkingLotService.getInstance());
        }

        @DisplayName("no spots created")
        @Test
        void testGetParkingSpotIdsForColorOfVehicleWhenNoSpotsCreated() {
            assertDoesNotThrow(()->parkingLotService.createParkingLot(3));
            when(parkingLotService.getParkingLotSize()).thenReturn(0);
            assertThrows(ParkingLotException.class,()->parkingLotService.getParkedSpotIdsForColourOfVehicle("White"));
        }

        @DisplayName("all spots are empty")
        @Test
        void testGetParkingSpotIdsForColorOfVehicleWhenSpotsEmpty(){
            assertDoesNotThrow(()->parkingLotService.createParkingLot(2));
            assertThrows(ParkingLotException.class, ()->parkingLotService.getParkingLotStatus());
            assertThrows(ParkingLotException.class,()->parkingLotService.getParkedSpotIdsForColourOfVehicle("Black"));
        }

        @DisplayName("no colour match found")
        @Test
        void testGetParkingSpotIdsForColorOfVehicleWhenMatchNotFound(){
            Vehicle vehicle = spy(new Car("CAR99","Blue"));
            assertDoesNotThrow(()->parkingLotService.createParkingLot(2));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertDoesNotThrow(()->parkingLotService.getParkingLotStatus());
            assertThrows(ParkingLotException.class,()->parkingLotService.getParkedSpotIdsForColourOfVehicle("Black"));
        }
        @DisplayName("correct colour match found")
        @Test
        void testGetParkingSpotIdsForColorOfVehicleWhenMatchFound(){
            Vehicle vehicle = spy(new Car("CAR100","Brown"));
            assertDoesNotThrow(()->parkingLotService.createParkingLot(2));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertDoesNotThrow(()->parkingLotService.getParkingLotStatus());
            assertDoesNotThrow(()->parkingLotService.getParkedSpotIdsForColourOfVehicle(vehicle.getColor()));
        }
    }
}