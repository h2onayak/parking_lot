package parking_lot.commands;

import parking_lot.enums.CommandEnum;
import parking_lot.enums.ResponseStatus;
import parking_lot.exception.ParkingLotException;
import parking_lot.response.Response;
import parking_lot.service.ParkingLotService;

import static parking_lot.enums.CommandEnum.REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR;

public class RegistrationNumbersForVehicleColourCommand extends Command {
    private final String expectedParameterInputs;

    public RegistrationNumbersForVehicleColourCommand(ParkingLotService parkingLotService, String[] commandInputs) {
        super(parkingLotService, CommandEnum.REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR, commandInputs);
        expectedParameterInputs = String.format("Required: %s <color>", REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR);

    }

    @Override
    public Response execute() throws ParkingLotException {
        if (commandInputs == null || commandInputs.length != 2)
            throw new ParkingLotException(ResponseStatus.NOT_ACCEPTABLE, expectedParameterInputs);
        String colour = commandInputs[1];
        return parkingLotService.getRegistrationNumbersForColor(colour);
    }
}
