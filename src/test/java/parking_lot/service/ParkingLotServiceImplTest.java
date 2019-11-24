package parking_lot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import parking_lot.exception.ParkingLotException;

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
            assertDoesNotThrow(()->parkingLotService.createParkingLot(5));
            assertEquals(numberOfSpots, parkingLotService.getParkingLotSize());
        }

        @DisplayName("parking spots are already exist")
        @Test
        void testWhenSpotsAreAlreadyCreated(){
            when(parkingLotService.getParkingLotSize()).thenReturn(1);
            assertThrows(ParkingLotException.class,()->parkingLotService.createParkingLot(2));
        }
    }
}