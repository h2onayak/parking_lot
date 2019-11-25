package parking_lot.controller;

import parking_lot.commands.Command;
import parking_lot.commands.CommandFactory;
import parking_lot.exception.ParkingLotException;
import parking_lot.exception.ParkingLotExceptionHandler;
import parking_lot.response.Response;
import parking_lot.service.ParkingLotService;

import java.util.Objects;

public abstract class BookingController implements Controller {
    private final ParkingLotService parkingLotService;

    public BookingController() {
        parkingLotService = ParkingLotService.getInstance();
    }

    static BookingController getInstance(String fileName) {
        if (Objects.isNull(fileName) || fileName.isEmpty())
            return ManualInputBookingController.getInstance();
        return FileProcessBookingController.getInstance(fileName);
    }

    public abstract void fetchCommands();

    @Override
    public String processCommand(String commandData) {
        Response response;
        try {
            Command command = CommandFactory.getCommand(parkingLotService, commandData);
            response = command.execute();
            return response.getMessage();
        } catch (ParkingLotException e) {
            response = ParkingLotExceptionHandler.handleException(e);
        }
        return response.getResolution();
    }
}
