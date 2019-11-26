package parking_lot.commands;

import org.junit.jupiter.api.*;
import parking_lot.commands.ParkCommand;
import parking_lot.enums.CommandEnum;
import parking_lot.exception.ParkingLotException;
import parking_lot.model.Car;
import parking_lot.model.base.Vehicle;
import parking_lot.service.ParkingLotService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class ParkCommandTest {

    @DisplayName("Execute Park in Parking lot command when")
    @Nested
    class ParkCommandTests {
        ParkingLotService parkingLotService;

        @BeforeEach
        void setUp() {
            parkingLotService = spy(ParkingLotService.getInstance());
            assertDoesNotThrow(() -> parkingLotService.createParkingLot(3));
        }
        @AfterEach
        void tearDown(){
            ParkingLotService.resetInstance();
            parkingLotService = null;
        }
        @DisplayName("command inputs shortage")
        @Test
        void testParkCommandWhenShortageOfInputs() {
            ParkCommand parkCommand = spy(new ParkCommand(parkingLotService,
                    new String[]{CommandEnum.PARK.getName()}));
            assertThrows(ParkingLotException.class, parkCommand::execute);
        }

        @DisplayName("command inputs more than required")
        @Test
        void testParkCommandWhenMoreInputs() {
            ParkCommand parkCommand = spy(new ParkCommand(parkingLotService,
                    new String[]{CommandEnum.PARK.getName(), "KA01", "white","black"}));
            assertThrows(ParkingLotException.class, parkCommand::execute);
        }

        @DisplayName("no command inputs")
        @Test
        void testParkCommandWhenNoInputs() {
            ParkCommand parkCommand = spy(new ParkCommand(parkingLotService, null));
            assertThrows(ParkingLotException.class, parkCommand::execute);
        }

        @DisplayName("empty command inputs")
        @Test
        void testParkCommandWhenEmptyInputs() {
            ParkCommand parkCommand = spy(new ParkCommand(parkingLotService, new String[]{}));
            assertThrows(ParkingLotException.class, parkCommand::execute);
        }

        @DisplayName("valid command inputs")
        @Test
        void testParkCommandWhenValidInputs() throws ParkingLotException {
            Vehicle vehicle =spy(new Car("KA01","White"));
            ParkCommand parkCommand = spy(new ParkCommand(parkingLotService,
                    new String[]{CommandEnum.PARK.getName(), vehicle.getRegistrationNumber(), vehicle.getColor()}));
            assertDoesNotThrow(parkCommand::execute);
            verify(parkingLotService).park(any());
        }
    }
}