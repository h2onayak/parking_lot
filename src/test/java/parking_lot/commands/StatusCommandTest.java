package parking_lot.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import parking_lot.commands.StatusCommand;
import parking_lot.enums.CommandEnum;
import parking_lot.exception.ParkingLotException;
import parking_lot.model.Car;
import parking_lot.model.base.Vehicle;
import parking_lot.service.ParkingLotService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class StatusCommandTest {

    @DisplayName("Execute Status command when")
    @Nested
    class StatusCommandTests {
        ParkingLotService parkingLotService;

        @BeforeEach
        void setUp() {
            parkingLotService = spy(ParkingLotService.getInstance());
        }

        @DisplayName("spots not created")
        @Test
        void testStatusCommandWhenWhenSpotsNotCreated() {
            StatusCommand statusCommand = spy(new StatusCommand(parkingLotService,
                    new String[]{CommandEnum.STATUS.getName()}));
            when(parkingLotService.getParkingLotSize()).thenReturn(0);
            assertThrows(ParkingLotException.class, statusCommand::execute);
        }


        @DisplayName("spots are empty")
        @Test
        void testStatusCommandWhenSpotsEmpty() {
            StatusCommand statusCommand = spy(new StatusCommand(parkingLotService,
                    new String[]{CommandEnum.STATUS.getName()}));
            assertDoesNotThrow(()->parkingLotService.createParkingLot(3));
            assertThrows(ParkingLotException.class, statusCommand::execute);
        }

        @DisplayName("vehicles present on spots")
        @Test
        void testStatusCommandWhenWhenVehiclePreset() throws ParkingLotException {
            StatusCommand statusCommand = spy(new StatusCommand(parkingLotService,
                    new String[]{CommandEnum.STATUS.getName()}));
            Vehicle vehicle = spy(new Car("CAR99","White"));
            assertDoesNotThrow(()->parkingLotService.createParkingLot(3));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertDoesNotThrow(statusCommand::execute);
            verify(parkingLotService).getParkingLotStatus();
        }
    }
}