package parking_lot.commands;

import parking_lot.enums.CommandEnum;
import parking_lot.enums.ResponseStatus;
import parking_lot.exception.ParkingLotException;
import parking_lot.response.Response;
import parking_lot.service.ParkingLotService;

import static parking_lot.enums.CommandEnum.LEAVE;

public class LeaveCommand extends Command {
    private final String expectedParameterInputs;

    public LeaveCommand(ParkingLotService parkingLotService, String[] commandInputs) {
        super(parkingLotService, CommandEnum.LEAVE, commandInputs);
        expectedParameterInputs = String.format("Required: %s <slot number>", LEAVE);

    }

    @Override
    public Response execute() throws ParkingLotException {
        int slotId;
        if (commandInputs == null || commandInputs.length != 2)
            throw new ParkingLotException(ResponseStatus.NOT_ACCEPTABLE, expectedParameterInputs);
        try {
            slotId = Integer.parseInt(commandInputs[1]);
        } catch (Exception e) {
            throw new ParkingLotException(ResponseStatus.NOT_ACCEPTABLE, expectedParameterInputs);
        }
        return parkingLotService.exit(slotId);
    }
}
