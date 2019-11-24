package parking_lot.commands;

import parking_lot.enums.CommandEnum;
import parking_lot.exception.ParkingLotException;
import parking_lot.response.Response;
import parking_lot.service.ParkingLotService;

public class SpotIdForRegistrationNumberCommand extends Command {
    public SpotIdForRegistrationNumberCommand(ParkingLotService parkingLotService, String[] commandInputs) {
        super(parkingLotService, CommandEnum.SLOT_NUMBER_FOR_REGISTRATION_NUMBER,commandInputs);
    }

    @Override
    public Response execute() throws ParkingLotException {
        return null;
    }
}
