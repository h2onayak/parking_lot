package parking_lot.commands;

import parking_lot.enums.CommandEnum;
import parking_lot.exception.ParkingLotException;
import parking_lot.response.Response;
import parking_lot.service.ParkingLotService;

public class ParkingLotCommand extends Command {
    public ParkingLotCommand(ParkingLotService parkingLotService, String[] commandInputs) {
        super(parkingLotService, CommandEnum.CREATE_PARKING_LOT, commandInputs);
    }

    @Override
    public Response execute() throws ParkingLotException {
        return null;
    }
}
