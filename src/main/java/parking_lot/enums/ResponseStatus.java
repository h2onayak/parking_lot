package parking_lot.enums;

public enum ResponseStatus {
    BAD_REQUEST(400, "Bad Request");

    private int statusCode;
    private String message;

    ResponseStatus(int _statusCode, String _message) {
        statusCode = _statusCode;
        message = _message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
