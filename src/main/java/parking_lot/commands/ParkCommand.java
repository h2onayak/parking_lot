package parking_lot.commands;

import parking_lot.enums.CommandEnum;
import parking_lot.exception.ParkingLotException;
import parking_lot.response.Response;
import parking_lot.service.ParkingLotService;

public class ParkCommand extends Command {
    public ParkCommand(ParkingLotService parkingLotService, String[] commandInputs) {
        super(parkingLotService, CommandEnum.PARK,commandInputs);
    }

    @Override
    public Response execute() throws ParkingLotException {
        return null;
    }
}
