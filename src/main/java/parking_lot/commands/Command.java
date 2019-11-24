package parking_lot.commands;

import parking_lot.enums.CommandEnum;
import parking_lot.exception.ParkingLotException;
import parking_lot.response.Response;
import parking_lot.service.ParkingLotService;

public abstract class Command {
    final ParkingLotService parkingLotService;
    final CommandEnum commandName;
    final String[] commandInputs;

    public Command(ParkingLotService parkingLotService, CommandEnum commandName, String[] commandInputs) {
        this.parkingLotService = parkingLotService;
        this.commandName = commandName;
        this.commandInputs = commandInputs;
    }

    public abstract Response execute() throws ParkingLotException;
}
