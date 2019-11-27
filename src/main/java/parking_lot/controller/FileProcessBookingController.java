package parking_lot.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class FileProcessBookingController extends BookingController {
    private final static Object mutex = new Object();
    private static FileProcessBookingController fileProcessBookingController = null;
    private final String fileName;

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
        processInputFile();
    }

    private void processInputFile() {
        BufferedReader bufferedReader = null;
        try (FileReader fileReader = new FileReader(fileName)) {
            bufferedReader = new BufferedReader(fileReader);
            executeCommandsFromFile(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(bufferedReader))
                    bufferedReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void executeCommandsFromFile(BufferedReader bufferedReader) throws IOException {
        String currentLine,result;
        while ((currentLine = bufferedReader.readLine()) != null) {
            result = processCommand(currentLine);
            System.out.println(result);
            if(result.equalsIgnoreCase("exit"))
                return;
        }
    }
}
