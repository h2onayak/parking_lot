package parking_lot.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import parking_lot.enums.CommandEnum;
import parking_lot.exception.ParkingLotException;
import parking_lot.model.Car;
import parking_lot.model.base.Vehicle;
import parking_lot.service.ParkingLotService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class LeaveCommandTest {

    @DisplayName("Execute Leave Parking lot command when")
    @Nested
    class LeaveCommandTests {
        ParkingLotService parkingLotService;

        @BeforeEach
        void setUp() {
            parkingLotService = spy(ParkingLotService.getInstance());
            assertDoesNotThrow(() -> parkingLotService.createParkingLot(3));
        }

        @DisplayName("command inputs are not valid")
        @Test
        void testLeaveCommandWhenNotValidInputs() {
            LeaveCommand leaveCommand = spy(new LeaveCommand(parkingLotService,
                    new String[]{CommandEnum.LEAVE.getName(), "not valid"}));
            assertThrows(ParkingLotException.class, leaveCommand::execute);
        }

        @DisplayName("command inputs shortage")
        @Test
        void testLeaveCommandWhenShortageOfInputs() {
            LeaveCommand leaveCommand = spy(new LeaveCommand(parkingLotService,
                    new String[]{CommandEnum.LEAVE.getName()}));
            assertThrows(ParkingLotException.class, leaveCommand::execute);
        }

        @DisplayName("command inputs more than required")
        @Test
        void testLeaveCommandWhenMoreInputs() {
            LeaveCommand leaveCommand = spy(new LeaveCommand(parkingLotService,
                    new String[]{CommandEnum.LEAVE.getName(), "6", "7"}));
            assertThrows(ParkingLotException.class, leaveCommand::execute);
        }

        @DisplayName("no command inputs")
        @Test
        void testLeaveCommandWhenNoInputs() {
            LeaveCommand leaveCommand = spy(new LeaveCommand(parkingLotService, null));
            assertThrows(ParkingLotException.class, leaveCommand::execute);
        }

        @DisplayName("empty command inputs")
        @Test
        void testLeaveCommandWhenEmptyInputs() {
            LeaveCommand leaveCommand = spy(new LeaveCommand(parkingLotService, new String[]{}));
            assertThrows(ParkingLotException.class, leaveCommand::execute);
        }

        @DisplayName("valid command inputs")
        @Test
        void testLeaveCommandWhenValidInputs() throws ParkingLotException {
            LeaveCommand leaveCommand = spy(new LeaveCommand(parkingLotService,
                    new String[]{CommandEnum.LEAVE.getName(), "1"}));
            Vehicle vehicle = spy(new Car("REG011", "Silver"));
            assertDoesNotThrow(() -> parkingLotService.park(vehicle));
            assertDoesNotThrow(leaveCommand::execute);
            verify(parkingLotService).exit(1);
        }
    }
}