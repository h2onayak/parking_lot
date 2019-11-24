package parking_lot.response;

import parking_lot.enums.ResponseStatus;

import java.time.LocalDateTime;

public class Response {
    private ResponseStatus responseStatus;
    private String message;
    private String resolution;
    private LocalDateTime timestamp;

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
        this.timestamp = LocalDateTime.now();
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getResolution() {
        return resolution;
    }
}
