package parking_lot.controller;


import java.util.Scanner;

public class ManualInputBookingController extends BookingController {
    private final static Object mutex = new Object();
    private static ManualInputBookingController manualInputBookingController = null;

    private ManualInputBookingController() {
    }

    public static ManualInputBookingController getInstance() {
        if (manualInputBookingController == null) {
            synchronized (mutex) {
                if (manualInputBookingController == null) {
                    manualInputBookingController = new ManualInputBookingController();
                }
            }
        }
        return manualInputBookingController;
    }

    @Override
    public void fetchCommands() {
        Scanner scanner = new Scanner(System.in);
        String result;
        while (true) {
            String commandLine = scanner.nextLine();
            result = processCommand(commandLine);
            System.out.println(result);
            if(result.equalsIgnoreCase("exit"))
                return;
        }
    }
}
