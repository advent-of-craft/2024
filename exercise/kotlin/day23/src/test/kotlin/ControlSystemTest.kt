import core.control.ControlSystem
import core.control.SleighAction
import core.control.SleighEngineStatus
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class ControlSystemTest : StringSpec({
    val outputStreamCaptor = ByteArrayOutputStream()

    beforeTest {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    afterTest {
        System.setOut(System.out)
    }

    "testStart" {
        // The system has been started
        val controlSystem = ControlSystem()
        controlSystem.action = SleighAction.FLYING
        controlSystem.status = SleighEngineStatus.OFF
        controlSystem.startSystem()
        controlSystem.status shouldBe SleighEngineStatus.ON
        outputStreamCaptor.toString().trim() shouldBe "Starting the sleigh...\nSystem ready."
    }

    "testAscend" {
        val controlSystem = ControlSystem()
        controlSystem.startSystem()
        controlSystem.ascend()
        controlSystem.action shouldBe SleighAction.FLYING
        outputStreamCaptor.toString().trim() shouldBe "Starting the sleigh...\nSystem ready.\nAscending..."
    }

    "testDescend" {
        val controlSystem = ControlSystem()
        controlSystem.startSystem()
        controlSystem.ascend()
        controlSystem.descend()
        controlSystem.action shouldBe SleighAction.HOVERING
        outputStreamCaptor.toString()
            .trim() shouldBe "Starting the sleigh...\nSystem ready.\nAscending...\nDescending..."
    }

    "testPark" {
        val controlSystem = ControlSystem()
        controlSystem.startSystem()
        controlSystem.park()
        controlSystem.action shouldBe SleighAction.PARKED
    }
})