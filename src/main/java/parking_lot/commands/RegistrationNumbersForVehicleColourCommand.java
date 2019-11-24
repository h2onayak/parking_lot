package parking_lot.commands;

import parking_lot.enums.CommandEnum;
import parking_lot.exception.ParkingLotException;
import parking_lot.response.Response;
import parking_lot.service.ParkingLotService;

public class RegistrationNumbersForVehicleColourCommand extends Command {
    public RegistrationNumbersForVehicleColourCommand(ParkingLotService parkingLotService, String[] commandInputs) {
        super(parkingLotService, CommandEnum.REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR,commandInputs);
    }

    @Override
    public Response execute() throws ParkingLotException {
        return null;
    }
}
