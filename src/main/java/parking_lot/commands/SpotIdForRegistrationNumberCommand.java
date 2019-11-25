package parking_lot.commands;

import parking_lot.enums.CommandEnum;
import parking_lot.enums.ResponseStatus;
import parking_lot.exception.ParkingLotException;
import parking_lot.response.Response;
import parking_lot.service.ParkingLotService;

public class SpotIdForRegistrationNumberCommand extends Command {
    private final String expectedParameterInputs;

    public SpotIdForRegistrationNumberCommand(ParkingLotService parkingLotService, String[] commandInputs) {
        super(parkingLotService, CommandEnum.SLOT_NUMBER_FOR_REGISTRATION_NUMBER, commandInputs);
        expectedParameterInputs = String.format("Required: %s <registration number>", CommandEnum.SLOT_NUMBER_FOR_REGISTRATION_NUMBER);

    }

    @Override
    public Response execute() throws ParkingLotException {
        if (commandInputs == null || commandInputs.length != 2)
            throw new ParkingLotException(ResponseStatus.NOT_ACCEPTABLE, expectedParameterInputs);
        String registrationNumber = commandInputs[1];
        return parkingLotService.getParkedSpotIdForRegistrationNumber(registrationNumber);
    }
}
