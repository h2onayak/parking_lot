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

class SpotIdsForVehicleColourCommandTest {
    
    @DisplayName("Execute SpotIds for Vehicle Colour when")
    @Nested
    class SpotIdsForVehicleColourCommandTests {
        ParkingLotService parkingLotService;

        @BeforeEach
        void setUp() {
            parkingLotService = spy(ParkingLotService.getInstance());
            assertDoesNotThrow(() -> parkingLotService.createParkingLot(4));
        }

        @DisplayName("command inputs shortage")
        @Test
        void testSpotIdsForVehicleColourCommandWhenShortageOfInputs() {
            SpotIdsForVehicleColourCommand spotIdsForVehicleColourCommand =
                    spy(new SpotIdsForVehicleColourCommand(parkingLotService,
                            new String[]{CommandEnum.SLOT_NUMBERS_FOR_CARS_WITH_COLOUR.getName()}));
            assertThrows(ParkingLotException.class, spotIdsForVehicleColourCommand::execute);
        }

        @DisplayName("command inputs more than required")
        @Test
        void testSpotIdsForVehicleColourCommandWhenMoreInputs() {
            SpotIdsForVehicleColourCommand spotIdsForVehicleColourCommand =
                    spy(new SpotIdsForVehicleColourCommand(parkingLotService,
                            new String[]{CommandEnum.SLOT_NUMBERS_FOR_CARS_WITH_COLOUR.getName(), "Blue", "Red"}));
            assertThrows(ParkingLotException.class, spotIdsForVehicleColourCommand::execute);
        }

        @DisplayName("no command inputs")
        @Test
        void testSpotIdsForVehicleColourCommandWhenNoInputs() {
            SpotIdsForVehicleColourCommand spotIdsForVehicleColourCommand =
                    spy(new SpotIdsForVehicleColourCommand(parkingLotService, null));
            assertThrows(ParkingLotException.class, spotIdsForVehicleColourCommand::execute);
        }

        @DisplayName("empty command inputs")
        @Test
        void testSpotIdsForVehicleColourCommandWhenEmptyInputs() {
            SpotIdsForVehicleColourCommand spotIdsForVehicleColourCommand =
                    spy(new SpotIdsForVehicleColourCommand(parkingLotService, new String[]{}));
            assertThrows(ParkingLotException.class, spotIdsForVehicleColourCommand::execute);
        }

        @DisplayName("valid command inputs and match not found")
        @Test
        void testSpotIdsForVehicleColourCommandWhenMatchNotFound(){
            SpotIdsForVehicleColourCommand spotIdsForVehicleColourCommand =
                    spy(new SpotIdsForVehicleColourCommand(parkingLotService,
                            new String[]{CommandEnum.SLOT_NUMBERS_FOR_CARS_WITH_COLOUR.getName(), "White"}));
            Vehicle vehicle = spy(new Car("CAR50","Blue"));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertThrows(ParkingLotException.class,spotIdsForVehicleColourCommand::execute);
        }

        @DisplayName("valid command inputs and match found")
        @Test
        void testSpotIdsForVehicleColourCommandWhenMatchFound() throws ParkingLotException {
            SpotIdsForVehicleColourCommand spotIdsForVehicleColourCommand =
                    spy(new SpotIdsForVehicleColourCommand(parkingLotService,
                            new String[]{CommandEnum.SLOT_NUMBERS_FOR_CARS_WITH_COLOUR.getName(), "Red"}));
            Vehicle vehicle = spy(new Car("CAR50","Red"));
            assertDoesNotThrow(()->parkingLotService.park(vehicle));
            assertDoesNotThrow(spotIdsForVehicleColourCommand::execute);
            verify(parkingLotService).getParkedSpotIdsForColourOfVehicle("Red");
        }
    }
}