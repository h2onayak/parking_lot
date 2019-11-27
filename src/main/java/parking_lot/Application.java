package parking_lot;

import parking_lot.controller.Controller;

class Application {
    public static void main(String[] args) {
        run(args.length >= 1 ? args[0] : "");
    }

    private static void run(String fileName) {
        Controller controller = Controller.getInstance(fileName.isEmpty() ? null : fileName);
        controller.fetchCommands();
    }
}
