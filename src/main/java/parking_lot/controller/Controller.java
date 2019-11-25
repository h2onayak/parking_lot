package parking_lot.controller;

public interface Controller {
    static Controller getInstance(String fileName) {
        return BookingController.getInstance(fileName);
    }

    void fetchCommands();

    String processCommand(String commandData);
}
