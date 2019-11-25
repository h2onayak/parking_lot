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
import static org.mockito.Mockito.spy;


class CommandFactoryTest {

    @DisplayName("Instantiating Command from Factory when")
    @Nested
    class CommandFactoryTests {
        ParkingLotService parkingLotService;

        @BeforeEach
        void setUp() {
            parkingLotService = spy(ParkingLotService.getInstance());
        }

        @DisplayName("command name is invalid")
        @Test
        void testGetCommandWhenCommandNameIsInvalid() {
            assertThrows(ParkingLotException.class, () -> CommandFactory.getCommand(parkingLotService,
                    "Invalid"));
        }

        @DisplayName("no command name")
        @Test
        void testGetCommandWhenNoCommandName() {
            assertThrows(ParkingLotException.class, () -> CommandFactory.getCommand(parkingLotService,
                    ""));
        }

        @DisplayName("command name is create_parking_lot")
        @Test
        void testGetCommandWhenCommandNameIsCreateParkingLot() {
            assertDoesNotThrow(() -> CommandFactory.getCommand(parkingLotService,
                    CommandEnum.CREATE_PARKING_LOT.getName()));
        }

        @DisplayName("command name is park")
        @Test
        void testGetCommandWhenCommandNameIsPark() {
            assertDoesNotThrow(() -> CommandFactory.getCommand(parkingLotService,
                    CommandEnum.PARK.getName()));
        }

        @DisplayName("command name is leave")
        @Test
        void testGetCommandWhenCommandNameIsLeave() {
            assertDoesNotThrow(() -> CommandFactory.getCommand(parkingLotService,
                    CommandEnum.LEAVE.getName()));
        }

        @DisplayName("command name is status")
        @Test
        void testGetCommandWhenCommandNameIsStatus() {
            assertDoesNotThrow(() -> CommandFactory.getCommand(parkingLotService,
                    CommandEnum.STATUS.getName()));
        }

        @DisplayName("command name is registration_numbers_for_cars_with_colour")
        @Test
        void testGetCommandWhenCommandNameIsRegistrationNumberForColour() {
            assertDoesNotThrow(() -> CommandFactory.getCommand(parkingLotService,
                    CommandEnum.REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR.getName()));
        }

        @DisplayName("command name is slot_numbers_for_cars_with_colour")
        @Test
        void testGetCommandWhenCommandNameIsSlotNumberForCarsColour() {
            assertDoesNotThrow(() -> CommandFactory.getCommand(parkingLotService,
                    CommandEnum.SLOT_NUMBERS_FOR_CARS_WITH_COLOUR.getName()));
        }

        @DisplayName("command name is slot_number_for_registration_number")
        @Test
        void testGetCommandWhenCommandNameIsSlotNumberForRegistrationNumber() {
            assertDoesNotThrow(() -> CommandFactory.getCommand(parkingLotService,
                    CommandEnum.SLOT_NUMBER_FOR_REGISTRATION_NUMBER.getName()));
        }
    }
}