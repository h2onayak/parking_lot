package parking_lot.exception;

import parking_lot.enums.ResponseStatus;
import parking_lot.response.Response;

public class ParkingLotExceptionHandler {
    public static Response handleException(ParkingLotException e) {
        Response response;
        if (e.getResponseStatus() != null) {
            response = new Response(e.getResponseStatus(), e.getMessage(),e.getResolution());
        } else {
            response = new Response(ResponseStatus.BAD_REQUEST, e.getMessage(), e.getResolution());
        }
        return response;
    }
}
