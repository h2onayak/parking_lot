package parking_lot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import parking_lot.constants.Constant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManualInputBookingControllerTest {

    @DisplayName("Processing booking from command prompt inputs when")
    @Nested
    class ManualInputBookingControllerTests {
        Controller controller = null;

        @BeforeEach
        void setUp() {
            controller = spy(Controller.getInstance(""));
        }

        @DisplayName("input is valid command")
        @Test
        void testProcessCommandWhenInputIsValid(){
           assertEquals(String.format(Constant.SLOT_CREATED_MESSAGE,6), controller.processCommand("create_parking_lot 6"));
        }

        @DisplayName("input is not valid command")
        @Test
        void testProcessCommandWhenInputIsNotValid(){
            assertEquals(Constant.INVALID_COMMAND,controller.processCommand("invalid"));
        }

        @DisplayName("input is exit command")
        @Test
        void testProcessCommandWhenInputIsExit(){
            assertEquals(Constant.EXIT,controller.processCommand("exit"));
        }
    }
}