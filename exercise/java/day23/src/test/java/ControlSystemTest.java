import core.control.ControlSystem;
import core.control.SleighAction;
import core.control.SleighEngineStatus;
import core.control.SleighNotStartedException;
import core.control.ReindeersNeedRestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class TestControlSystem {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testStart() {
        // The system has been started
        ControlSystem controlSystem = new ControlSystem();
        controlSystem.action = SleighAction.FLYING;
        controlSystem.status = SleighEngineStatus.OFF;
        controlSystem.startSystem();
        assertTrue(controlSystem.status == SleighEngineStatus.ON);
        assertEquals("Starting the sleigh...\r\n" + "System ready.",
                outputStreamCaptor.toString().trim());
    }

    @Test
    void testAscend() throws ReindeersNeedRestException, SleighNotStartedException {
        ControlSystem controlSystem = new ControlSystem();
        controlSystem.startSystem();
        controlSystem.ascend();
        assertTrue(controlSystem.action == SleighAction.FLYING);
        assertEquals(
                """
                        Starting the sleigh...\r
                        System ready.\r
                        Ascending...""",
                outputStreamCaptor.toString().trim());
    }

    @Test
    void testDescend() throws ReindeersNeedRestException, SleighNotStartedException {
        ControlSystem controlSystem = new ControlSystem();
        controlSystem.startSystem();
        controlSystem.ascend();
        controlSystem.descend();
        assertTrue(controlSystem.action == SleighAction.HOVERING);
        assertEquals(
                """
                        Starting the sleigh...\r
                        System ready.\r
                        Ascending...\r
                        Descending...""",
                outputStreamCaptor.toString().trim());
    }

    @Test
    void testPark() {
        // That would be nice to test it
        assertNotNull(new ControlSystem());
    }
}