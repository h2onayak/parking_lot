package parking_lot.commands;

import parking_lot.enums.CommandEnum;
import parking_lot.enums.ResponseStatus;
import parking_lot.response.Response;
import parking_lot.service.ParkingLotService;

import static parking_lot.constants.Constant.EXIT;

public class ExitCommand extends Command {
    public ExitCommand(ParkingLotService parkingLotService, String[] commandInputs) {
        super(parkingLotService, CommandEnum.EXIT, commandInputs);
    }
    @Override
    public Response execute() {
        return new Response(ResponseStatus.OK,EXIT);
    }
}
