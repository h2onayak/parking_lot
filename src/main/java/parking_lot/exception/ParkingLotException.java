package parking_lot.exception;

import parking_lot.enums.ResponseStatus;

public class ParkingLotException extends Exception {
    private ResponseStatus responseStatus;
    private final String resolution;

    public ParkingLotException(ResponseStatus responseStatus, String resolution) {
        this(responseStatus, responseStatus.getMessage(), resolution);
    }

    public ParkingLotException(ResponseStatus responseStatus, String message, String resolution) {
        super(message);
        this.responseStatus = responseStatus;
        this.resolution = resolution;
    }

    public ParkingLotException(String message) {
        super(message);
        this.resolution = message;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public String getResolution() {
        return resolution;
    }
}
