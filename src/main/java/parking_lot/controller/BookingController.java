package parking_lot.controller;

import parking_lot.enums.ResponseStatus;
import parking_lot.response.Response;
import parking_lot.service.ParkingLotService;

public abstract class BookingController implements Controller {
    private final ParkingLotService parkingLotService;

    public BookingController() {
        parkingLotService = null;//TODO: Get Singleton service object
    }

    public abstract void fetchCommands();

    @Override
    public String processCommand(String commandData) {
        //TODO: execute commands and get response
        Response response =  new Response(ResponseStatus.NOT_FOUND);
        return response.getResolution();
    }
}
