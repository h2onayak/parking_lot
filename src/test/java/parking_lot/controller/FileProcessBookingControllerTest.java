package parking_lot.controller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.spy;

class FileProcessBookingControllerTest {

    @DisplayName("Fetch commands process commands when")
    @Nested
    class FileProcessBookingControllerTests {
        @DisplayName("input file is test_file_input1.text")
        @Test
        void testFileProcessingFromInputFile1() {
            Controller controller = spy(Controller.getInstance("src/test/resources/test_file_input1.txt"));
            assertDoesNotThrow(controller::fetchCommands);
        }

        @DisplayName("input file is test_file_input2.text")
        @Test
        void testFileProcessingFromInputFile2() {
            Controller controller = spy(Controller.getInstance("src/test/resources/test_file_input2.txt"));
            assertDoesNotThrow(controller::fetchCommands);
        }

        @DisplayName("input file is test_file_input3.text")
        @Test
        void testFileProcessingFromInputFile3() {
            Controller controller = spy(Controller.getInstance("src/test/resources/test_file_input3.txt"));
            assertDoesNotThrow(controller::fetchCommands);
        }
        @DisplayName("input file is test_file_input4.text")
        @Test
        void testFileProcessingFromInputFile4() {
            Controller controller = spy(Controller.getInstance("src/test/resources/test_file_input4.txt"));
            assertDoesNotThrow(controller::fetchCommands);
        }
        @DisplayName("input file is test_file_input5.text")
        @Test
        void testFileProcessingFromInputFile5() {
            Controller controller = spy(Controller.getInstance("src/test/resources/test_file_input5.txt"));
            assertDoesNotThrow(controller::fetchCommands);
        }
    }
}