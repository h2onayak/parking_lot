package parking_lot.commands;

import org.junit.jupiter.api.*;
import parking_lot.enums.CommandEnum;
import parking_lot.exception.ParkingLotException;
import parking_lot.model.Car;
import parking_lot.model.base.Vehicle;
import parking_lot.service.ParkingLotService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class RegistrationNumbersForVehicleColourCommandTest {

    @DisplayName("Execute RegistrationNumbers For Vehicle Colour command when")
    @Nested
    class RegistrationNumbersForVehicleColourCommandTests {
        ParkingLotService parkingLotService;

        @BeforeEach
        void setUp() {
            ParkingLotService.resetInstance();
            parkingLotService = spy(ParkingLotService.getInstance());
            assertDoesNotThrow(() -> parkingLotService.createParkingLot(2));
        }
        @AfterEach
        void tearDown(){
            ParkingLotService.resetInstance();
            parkingLotService = null;
        }
        @DisplayName("command inputs shortage")
        @Test
        void testRegistrationNumbersForVehicleColourCommandWhenShortageOfInputs() {
            RegistrationNumbersForVehicleColourCommand registrationNumbersForVehicleColourCommand =
                    spy(new RegistrationNumbersForVehicleColourCommand(parkingLotService,
                            new String[]{CommandEnum.REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR.getName()}));
            assertThrows(ParkingLotException.class, registrationNumbersForVehicleColourCommand::execute);
        }

        @DisplayName("command inputs more than required")
        @Test
        void testRegistrationNumbersForVehicleColourCommandWhenMoreInputs() {
            RegistrationNumbersForVehicleColourCommand registrationNumbersForVehicleColourCommand =
                    spy(new RegistrationNumbersForVehicleColourCommand(parkingLotService,
                            new String[]{CommandEnum.REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR.getName(),
                                    "Red", "Blue", "Black"}));
            assertThrows(ParkingLotException.class, registrationNumbersForVehicleColourCommand::execute);
        }

        @DisplayName("no command inputs")
        @Test
        void testRegistrationNumbersForVehicleColourCommandWhenNoInputs() {
            RegistrationNumbersForVehicleColourCommand registrationNumbersForVehicleColourCommand =
                    spy(new RegistrationNumbersForVehicleColourCommand(parkingLotService, null));
            assertThrows(ParkingLotException.class, registrationNumbersForVehicleColourCommand::execute);
        }

        @DisplayName("empty command inputs")
        @Test
        void testRegistrationNumbersForVehicleColourCommandWhenEmptyInputs() {
            RegistrationNumbersForVehicleColourCommand registrationNumbersForVehicleColourCommand =
                    spy(new RegistrationNumbersForVehicleColourCommand(parkingLotService, new String[]{}));
            assertThrows(ParkingLotException.class, registrationNumbersForVehicleColourCommand::execute);
        }


        @DisplayName("valid command inputs and color match not found")
        @Test
        void testRegistrationNumbersForVehicleColourCommandWhenMatchNotFound() {
            RegistrationNumbersForVehicleColourCommand registrationNumbersForVehicleColourCommand =
                    spy(new RegistrationNumbersForVehicleColourCommand(parkingLotService,
                            new String[]{CommandEnum.REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR.getName(), "Black"}));
            Vehicle vehicle = spy(new Car("CAR002","Red"));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertThrows(ParkingLotException.class,registrationNumbersForVehicleColourCommand::execute);
        }

        @DisplayName("valid command inputs and color match found")
        @Test
        void testRegistrationNumbersForVehicleColourCommandWhenMatchFound() throws ParkingLotException {
            RegistrationNumbersForVehicleColourCommand registrationNumbersForVehicleColourCommand =
                    spy(new RegistrationNumbersForVehicleColourCommand(parkingLotService,
                            new String[]{CommandEnum.REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR.getName(), "Black"}));
            Vehicle vehicle = spy(new Car("CAR002","Black"));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertDoesNotThrow(registrationNumbersForVehicleColourCommand::execute);
            verify(parkingLotService).getRegistrationNumbersForColor("Black");
        }
    }
}