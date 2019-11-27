package parking_lot.service;

import org.junit.jupiter.api.*;
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

        @AfterEach
        void tearDown(){
            ParkingLotService.resetInstance();
            parkingLotService = null;

        }
        @DisplayName("number of spots are zero")
        @Test
        void testZeroParkingLotSize() {
            when(parkingLotService.getParkingLotSize()).thenReturn(0);
            assertThrows(ParkingLotException.class, () -> parkingLotService.createParkingLot(0));
        }

        @DisplayName("number of spots are negative")
        @Test
        void testNegativeParkingLotSize() {
            when(parkingLotService.getParkingLotSize()).thenReturn(0);
            assertThrows(ParkingLotException.class, () -> parkingLotService.createParkingLot(-1));
        }

        @DisplayName("number of spots are positive")
        @Test
        void testPositiveParkingLotSize() throws ParkingLotException {
            int numberOfSpots = 3;
            when(parkingLotService.getParkingLotSize()).thenReturn(0);
            assertDoesNotThrow(() -> parkingLotService.createParkingLot(numberOfSpots));
            verify(parkingLotService).createParkingLot(numberOfSpots);
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
            ParkingLotService.resetInstance();
            parkingLotService = spy(ParkingLotService.getInstance());
            assertDoesNotThrow(()->parkingLotService.createParkingLot(2));
        }
        @AfterEach
        void tearDown(){
            ParkingLotService.resetInstance();
            parkingLotService = null;

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
            assertDoesNotThrow(() -> parkingLotService.park(vehicle));
            assertThrows(ParkingLotException.class, () -> parkingLotService.park(vehicle));
        }

        @DisplayName("parking is full")
        @Test
        void testWhenParkingIsFull() {
            Vehicle vehicle1 = spy(new Car("KA 102", "white"));
            Vehicle vehicle2 = spy(new Car("KA 103", "Black"));
            Vehicle vehicle3 = spy(new Car("KA 104", "Red"));
            assertDoesNotThrow(() -> parkingLotService.park(vehicle1));
            assertDoesNotThrow(() -> parkingLotService.park(vehicle2));
            when(parkingLotService.getParkingLotSize()).thenReturn(2);
            assertThrows(ParkingLotException.class, () -> parkingLotService.park(vehicle3));
        }

        @DisplayName("parking space is present and vehicle is valid")
        @Test
        void testWhenParkingExistAndVehicleIsValid() {
            Vehicle vehicle = spy(new Car("KA 104", "white"));
            assertDoesNotThrow(() -> parkingLotService.park(vehicle));
        }
    }

    @DisplayName("Exit vehicle when")
    @Nested
    class ExitVehicleTests {
        ParkingLotService parkingLotService;

        @BeforeEach
        void setUp() {
            ParkingLotService.resetInstance();
            parkingLotService = spy(ParkingLotService.getInstance());
            assertDoesNotThrow(() -> parkingLotService.createParkingLot(1));
        }

        @AfterEach
        void tearDown(){
            ParkingLotService.resetInstance();
            parkingLotService = null;

        }

        @DisplayName("no parking spots created")
        @Test
        void testExitWhenNoSpotsCreated() {
            when(parkingLotService.getParkingLotSize()).thenReturn(0);
            assertThrows(ParkingLotException.class, () -> parkingLotService.exit(1));
        }

        @DisplayName("spot id does not exist")
        @Test
        void testWhenParkingSpotIdNotExist() {
            assertThrows(ParkingLotException.class, () -> parkingLotService.exit(2));
        }

        @DisplayName("no vehicle was parked on spot id")
        @Test
        void testWhenNoVehicleParkedOnSpotId() {
            assertThrows(ParkingLotException.class, () -> parkingLotService.exit(1));
        }

        @DisplayName("spot id is correct")
        @Test
        void testWhenThereIsCorrectSpotIdGiven() {
            Vehicle vehicle = spy(new Car("KA 104", "white"));
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
            ParkingLotService.resetInstance();
            parkingLotService = spy(ParkingLotService.getInstance());
            assertDoesNotThrow(()->parkingLotService.createParkingLot(2));
        }

        @AfterEach
        void tearDown(){
            ParkingLotService.resetInstance();
            parkingLotService = null;

        }

        @DisplayName("no spots are created")
        @Test
        void testParkingLotStatusWhenNoSpotsCreated() {
            when(parkingLotService.getParkingLotSize()).thenReturn(0);
            assertThrows(ParkingLotException.class,()->parkingLotService.getParkingLotStatus());
        }

        @DisplayName("spots are empty")
        @Test
        void testParkingLotStatusWhenSpotsAreEmpty(){
            assertThrows(ParkingLotException.class,()->parkingLotService.getParkingLotStatus());
        }

        @DisplayName("few spots are parked")
        @Test
        void testParkingLotStatusWhenNotFullyParked() throws ParkingLotException {
            Vehicle vehicle = spy(new Car("CAR05", "Red"));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertDoesNotThrow(()->parkingLotService.getParkingLotStatus());
            verify(parkingLotService).getParkingLotStatus();
        }

        @DisplayName("all spots are parked")
        @Test
        void testParkingLotStatusWhenFullyParked() {
            Vehicle vehicle1 = spy(new Car("CAR11", "White"));
            Vehicle vehicle2 = spy(new Car("CAR12", "Black"));
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
            ParkingLotService.resetInstance();
            parkingLotService = spy(ParkingLotService.getInstance());
            assertDoesNotThrow(()->parkingLotService.createParkingLot(3));
        }

        @AfterEach
        void tearDown(){
            ParkingLotService.resetInstance();
            parkingLotService = null;

        }

        @DisplayName("no spots created")
        @Test
        void testGetRegistrationNumbersOnColorForNoSpotsCreated() {
            when(parkingLotService.getParkingLotSize()).thenReturn(0);
            assertThrows(ParkingLotException.class,()->parkingLotService.getRegistrationNumbersForColor("White"));
        }

        @DisplayName("all spots are empty")
        @Test
        void testGetRegistrationNumbersOnColorForEmptyLot() {
            assertThrows(ParkingLotException.class,()->parkingLotService.getRegistrationNumbersForColor("Silver"));
        }

        @DisplayName("no colour match found")
        @Test
        void testGetRegistrationNumbersOnNoColorMatch() {
            Vehicle vehicle = spy(new Car("KA01KA02","maroon"));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertThrows(ParkingLotException.class,()->parkingLotService.getRegistrationNumbersForColor("Red"));
        }

        @DisplayName("correct color match found")
        @Test
        void testGetRegistrationNumbersOnColorMatch() throws ParkingLotException {
            Vehicle vehicle = spy(new Car("KA01KA03","Green"));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertDoesNotThrow(()->parkingLotService.getRegistrationNumbersForColor(vehicle.getColor()));
            verify(parkingLotService).getRegistrationNumbersForColor(vehicle.getColor());
        }
    }

    @DisplayName("Parked spot id on registration number when")
    @Nested
    class ParkedSpotIdOnRegistrationNumberTests {
        ParkingLotService parkingLotService;

        @BeforeEach
        void setUp() {
            ParkingLotService.resetInstance();
            parkingLotService = spy(ParkingLotService.getInstance());
            assertDoesNotThrow(()->parkingLotService.createParkingLot(3));
        }
        @AfterEach
        void tearDown(){
            ParkingLotService.resetInstance();
            parkingLotService = null;

        }
        @DisplayName("no spots created")
        @Test
        void testGetSpotIdOfRegistrationNumberWhenNoSpotsCreated() {
            when(parkingLotService.getParkingLotSize()).thenReturn(0);
            assertThrows(ParkingLotException.class,()->parkingLotService.getParkedSpotIdForRegistrationNumber("REG01"));
        }

        @DisplayName("all spots are empty")
        @Test
        void testGetSpotIdOfRegistrationNumberWhenSpotsAreEmpty() {
            assertThrows(ParkingLotException.class,()->parkingLotService
                    .getParkedSpotIdForRegistrationNumber("REG02"));
        }

        @DisplayName("no registration number match found")
        @Test
        void testGetSpotIdOfRegistrationNumberWhenNoRegistrationMatch() {
            Vehicle vehicle = spy(new Car("REG02","Blue"));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertThrows(ParkingLotException.class,()->parkingLotService
                    .getParkedSpotIdForRegistrationNumber("REG05"));
        }

        @DisplayName("correct registration number match found")
        @Test
        void testGetSpotIdOfRegistrationNumberWhenRegistrationMatched() {
            Vehicle vehicle = spy(new Car("REG02","Blue"));
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
            ParkingLotService.resetInstance();
            parkingLotService = spy(ParkingLotService.getInstance());
            assertDoesNotThrow(()->parkingLotService.createParkingLot(2));
        }
        @AfterEach
        void tearDown(){
            ParkingLotService.resetInstance();
            parkingLotService = null;
        }

        @DisplayName("no spots created")
        @Test
        void testGetParkingSpotIdsForColorOfVehicleWhenNoSpotsCreated() {
            when(parkingLotService.getParkingLotSize()).thenReturn(0);
            assertThrows(ParkingLotException.class,()->parkingLotService.getParkedSpotIdsForColourOfVehicle("White"));
        }

        @DisplayName("all spots are empty")
        @Test
        void testGetParkingSpotIdsForColorOfVehicleWhenSpotsEmpty(){
            assertThrows(ParkingLotException.class,()->parkingLotService.getParkedSpotIdsForColourOfVehicle("Black"));
        }

        @DisplayName("no colour match found")
        @Test
        void testGetParkingSpotIdsForColorOfVehicleWhenMatchNotFound(){
            Vehicle vehicle = spy(new Car("CAR99","Blue"));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertThrows(ParkingLotException.class,()->parkingLotService.getParkedSpotIdsForColourOfVehicle("Black"));
        }
        @DisplayName("correct colour match found")
        @Test
        void testGetParkingSpotIdsForColorOfVehicleWhenMatchFound() throws ParkingLotException {
            Vehicle vehicle = spy(new Car("CAR100","Brown"));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertDoesNotThrow(()->parkingLotService.getParkedSpotIdsForColourOfVehicle(vehicle.getColor()));
            verify(parkingLotService).getParkedSpotIdsForColourOfVehicle(vehicle.getColor());
        }
    }
}