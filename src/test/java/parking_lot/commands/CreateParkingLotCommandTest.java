package parking_lot.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import parking_lot.enums.CommandEnum;
import parking_lot.exception.ParkingLotException;
import parking_lot.service.ParkingLotService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CreateParkingLotCommandTest {

    @DisplayName("Execute Create Parking lot command when")
    @Nested
    class CreateParkingLotCommandTests {
        ParkingLotService parkingLotService;

        @BeforeEach
        void setUp() {
            parkingLotService = spy(ParkingLotService.getInstance());
        }

        @DisplayName("command inputs are not valid")
        @Test
        void testCreateParkingCommandWhenNotValidInputs() {
            CreateParkingLotCommand createParkingLotCommand = spy(new CreateParkingLotCommand(parkingLotService,
                    new String[]{CommandEnum.CREATE_PARKING_LOT.getName(), "not valid"}));
            assertThrows(ParkingLotException.class, createParkingLotCommand::execute);
        }

        @DisplayName("command inputs shortage")
        @Test
        void testCreateParkingCommandWhenShortageOfInputs() {
            CreateParkingLotCommand createParkingLotCommand = spy(new CreateParkingLotCommand(parkingLotService,
                    new String[]{CommandEnum.CREATE_PARKING_LOT.getName()}));
            assertThrows(ParkingLotException.class, createParkingLotCommand::execute);
        }

        @DisplayName("command inputs more than required")
        @Test
        void testCreateParkingCommandWhenMoreInputs() {
            CreateParkingLotCommand createParkingLotCommand = spy(new CreateParkingLotCommand(parkingLotService,
                    new String[]{CommandEnum.CREATE_PARKING_LOT.getName(), "6", "7"}));
            assertThrows(ParkingLotException.class, createParkingLotCommand::execute);
        }

        @DisplayName("no command inputs")
        @Test
        void testCreateParkingCommandWhenNoInputs() {
            CreateParkingLotCommand createParkingLotCommand = spy(new CreateParkingLotCommand(parkingLotService,
                    null));
            assertThrows(ParkingLotException.class, createParkingLotCommand::execute);
        }

        @DisplayName("empty command inputs")
        @Test
        void testCreateParkingCommandWhenEmptyInputs() {
            CreateParkingLotCommand createParkingLotCommand = spy(new CreateParkingLotCommand(parkingLotService,
                    new String[]{}));
            assertThrows(ParkingLotException.class, createParkingLotCommand::execute);
        }

        @DisplayName("valid command inputs")
        @Test
        void testCreateParkingCommandWhenValidInputs() throws ParkingLotException {
            CreateParkingLotCommand createParkingLotCommand = spy(new CreateParkingLotCommand(parkingLotService,
                    new String[]{CommandEnum.CREATE_PARKING_LOT.getName(), "6"}));
            assertDoesNotThrow(createParkingLotCommand::execute);
            verify(parkingLotService).createParkingLot(6);
        }

        @DisplayName("parking lot already exist")
        @Test
        void testCreateParkingCommandWhenAlreadyCreated(){
            CreateParkingLotCommand createParkingLotCommand = spy(new CreateParkingLotCommand(parkingLotService,
                    new String[]{CommandEnum.CREATE_PARKING_LOT.getName(), "6"}));
            when(parkingLotService.getParkingLotSize()).thenReturn(2);
            assertThrows(ParkingLotException.class,createParkingLotCommand::execute);
        }
    }
}