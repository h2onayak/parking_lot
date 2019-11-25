package parking_lot.controller;


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
        //TODO: Complete this
    }
}
