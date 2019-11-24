package parking_lot.commands;

import parking_lot.enums.CommandEnum;
import parking_lot.enums.ResponseStatus;
import parking_lot.exception.ParkingLotException;
import parking_lot.response.Response;
import parking_lot.service.ParkingLotService;

public class CreateParkingLotCommand extends Command {
    private final String expectedParameterInputs;

    public CreateParkingLotCommand(ParkingLotService parkingLotService, String[] commandInputs) {
        super(parkingLotService, CommandEnum.CREATE_PARKING_LOT, commandInputs);
        expectedParameterInputs = String.format("Required : %s <slots>", CommandEnum.CREATE_PARKING_LOT.getName());
    }

    @Override
    public Response execute() throws ParkingLotException {
        int numberOfSlots;
        if (commandInputs == null || commandInputs.length != 2)
            throw new ParkingLotException(ResponseStatus.NOT_ACCEPTABLE, expectedParameterInputs);
        try {
            numberOfSlots = Integer.parseInt(commandInputs[1]);
        } catch (Exception e) {
            throw new ParkingLotException(ResponseStatus.NOT_ACCEPTABLE, expectedParameterInputs);
        }
        return parkingLotService.createParkingLot(numberOfSlots);
    }
}
