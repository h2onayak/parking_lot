package parking_lot.response;

import parking_lot.enums.ResponseStatus;

public class Response {
    private final ResponseStatus responseStatus;
    private final String message;
    private final String resolution;

    public Response(ResponseStatus responseStatus) {
        this(responseStatus, null);
    }

    //Response: Invalid inputs -> show resolution
    public Response(ResponseStatus responseStatus, String message) {
        this(responseStatus, message, null);
    }

    // Response: Success -> Show message
    public Response(ResponseStatus responseStatus, String message, String resolution) {
        this.responseStatus = responseStatus;
        this.resolution = resolution;
        this.message = message != null ? message : responseStatus.getMessage();
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getResolution() {
        return resolution;
    }
}
