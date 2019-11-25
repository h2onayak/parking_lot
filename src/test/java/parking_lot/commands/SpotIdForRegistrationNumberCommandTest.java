package parking_lot.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import parking_lot.commands.SpotIdForRegistrationNumberCommand;
import parking_lot.enums.CommandEnum;
import parking_lot.exception.ParkingLotException;
import parking_lot.model.Car;
import parking_lot.model.base.Vehicle;
import parking_lot.service.ParkingLotService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class SpotIdForRegistrationNumberCommandTest {

    @DisplayName("Execute SpotId for RegistrationNumber when")
    @Nested
    class SpotIdForRegistrationNumberCommandTests {
        ParkingLotService parkingLotService;

        @BeforeEach
        void setUp() {
            parkingLotService = spy(ParkingLotService.getInstance());
            assertDoesNotThrow(() -> parkingLotService.createParkingLot(5));
        }

        @DisplayName("command inputs shortage")
        @Test
        void testSpotIdForRegistrationNumberCommandWhenShortageOfInputs() {
            SpotIdForRegistrationNumberCommand spotIdForRegistrationNumberCommand =
                    spy(new SpotIdForRegistrationNumberCommand(parkingLotService,
                            new String[]{CommandEnum.SLOT_NUMBER_FOR_REGISTRATION_NUMBER.getName()}));
            assertThrows(ParkingLotException.class, spotIdForRegistrationNumberCommand::execute);
        }

        @DisplayName("command inputs more than required")
        @Test
        void testSpotIdForRegistrationNumberCommandWhenMoreInputs() {
            SpotIdForRegistrationNumberCommand spotIdForRegistrationNumberCommand =
                    spy(new SpotIdForRegistrationNumberCommand(parkingLotService,
                            new String[]{CommandEnum.SLOT_NUMBER_FOR_REGISTRATION_NUMBER.getName(), "KA01", "KA02"}));
            assertThrows(ParkingLotException.class, spotIdForRegistrationNumberCommand::execute);
        }

        @DisplayName("no command inputs")
        @Test
        void testSpotIdForRegistrationNumberCommandWhenNoInputs() {
            SpotIdForRegistrationNumberCommand spotIdForRegistrationNumberCommand =
                    spy(new SpotIdForRegistrationNumberCommand(parkingLotService, null));
            assertThrows(ParkingLotException.class, spotIdForRegistrationNumberCommand::execute);
        }

        @DisplayName("empty command inputs")
        @Test
        void testSpotIdForRegistrationNumberCommandWhenEmptyInputs() {
            SpotIdForRegistrationNumberCommand spotIdForRegistrationNumberCommand =
                    spy(new SpotIdForRegistrationNumberCommand(parkingLotService, new String[]{}));
            assertThrows(ParkingLotException.class, spotIdForRegistrationNumberCommand::execute);
        }

        @DisplayName("valid command inputs and match not found")
        @Test
        void testSpotIdForRegistrationNumberCommandWhenMatchNotFound(){
            SpotIdForRegistrationNumberCommand spotIdForRegistrationNumberCommand =
                    spy(new SpotIdForRegistrationNumberCommand(parkingLotService,
                            new String[]{CommandEnum.SLOT_NUMBER_FOR_REGISTRATION_NUMBER.getName(), "KA01"}));
            Vehicle vehicle = spy(new Car("CAR035","Black"));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertThrows(ParkingLotException.class,spotIdForRegistrationNumberCommand::execute);
        }

        @DisplayName("valid command inputs and match found")
        @Test
        void testSpotIdForRegistrationNumberCommandWhenMatchFound() throws ParkingLotException {
            SpotIdForRegistrationNumberCommand spotIdForRegistrationNumberCommand =
                    spy(new SpotIdForRegistrationNumberCommand(parkingLotService,
                            new String[]{CommandEnum.SLOT_NUMBER_FOR_REGISTRATION_NUMBER.getName(), "KA01"}));
            Vehicle vehicle = spy(new Car("KA01","Black"));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertDoesNotThrow(spotIdForRegistrationNumberCommand::execute);
            verify(parkingLotService).getParkedSpotIdForRegistrationNumber("KA01");
        }
    }
}