package parking_lot.commands;

import parking_lot.enums.CommandEnum;
import parking_lot.enums.ResponseStatus;
import parking_lot.exception.ParkingLotException;
import parking_lot.model.Car;
import parking_lot.response.Response;
import parking_lot.service.ParkingLotService;

import static parking_lot.enums.CommandEnum.PARK;

public class ParkCommand extends Command {
    private final String expectedParameterInputs;

    public ParkCommand(ParkingLotService parkingLotService, String[] commandInputs) {
        super(parkingLotService, CommandEnum.PARK, commandInputs);
        expectedParameterInputs = String.format("Required: %s <registration number> <color>", PARK);

    }

    @Override
    public Response execute() throws ParkingLotException {
        if (commandInputs == null || commandInputs.length != 3)
            throw new ParkingLotException(ResponseStatus.NOT_ACCEPTABLE, expectedParameterInputs);
        String registrationNumber = commandInputs[1];
        String colour = commandInputs[2];
        return parkingLotService.park(new Car(registrationNumber, colour));
    }
}
