package parking_lot.commands;

import parking_lot.constants.Constant;
import parking_lot.enums.CommandEnum;
import parking_lot.enums.ResponseStatus;
import parking_lot.exception.ParkingLotException;
import parking_lot.service.ParkingLotService;

public class CommandFactory {

    public static Command getCommand(ParkingLotService parkingLotService, String commandData) throws ParkingLotException {
        String[] commandInputs = commandData.split(" ");
        String commandName = commandInputs[0];
        try {
            switch (CommandEnum.valueOf(commandName.toUpperCase())) {
                case CREATE_PARKING_LOT:
                    return new CreateParkingLotCommand(parkingLotService, commandInputs);
                case PARK:
                    return new ParkCommand(parkingLotService, commandInputs);
                case LEAVE:
                    return new LeaveCommand(parkingLotService, commandInputs);
                case STATUS:
                    return new StatusCommand(parkingLotService, commandInputs);
                case REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR:
                    return new RegistrationNumbersForVehicleColourCommand(parkingLotService, commandInputs);
                case SLOT_NUMBERS_FOR_CARS_WITH_COLOUR:
                    return new SpotIdsForVehicleColourCommand(parkingLotService, commandInputs);
                case SLOT_NUMBER_FOR_REGISTRATION_NUMBER:
                    return new SpotIdForRegistrationNumberCommand(parkingLotService, commandInputs);
                case EXIT:
                    return new ExitCommand(parkingLotService,commandInputs);
                default:
                    throw new ParkingLotException(ResponseStatus.BAD_REQUEST, Constant.INVALID_COMMAND);
            }
        } catch (Exception e) {
            throw new ParkingLotException(ResponseStatus.BAD_REQUEST, Constant.INVALID_COMMAND);
        }
    }
}
