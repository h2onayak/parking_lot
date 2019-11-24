package parking_lot.controller;

public interface Controller {
    void fetchCommands();

    String processCommand(String commandData);
}
