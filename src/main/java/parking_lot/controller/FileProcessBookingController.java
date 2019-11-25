package parking_lot.controller;

public class FileProcessBookingController extends BookingController {
    private final static Object mutex = new Object();
    private static FileProcessBookingController fileProcessBookingController = null;
    private String fileName;

    private FileProcessBookingController(String fileName) {
        this.fileName = fileName;
    }

    public static FileProcessBookingController getInstance(String fileName) {
        if (fileProcessBookingController == null) {
            synchronized (mutex) {
                if (fileProcessBookingController == null)
                    fileProcessBookingController = new FileProcessBookingController(fileName);
            }
        }
        return fileProcessBookingController;
    }

    @Override
    public void fetchCommands() {
        //TODO: Complete this
    }
}
