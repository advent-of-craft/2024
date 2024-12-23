import core.control.{ControlSystem, SleighAction, SleighEngineStatus}
import org.scalatest.BeforeAndAfter
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import java.io.{ByteArrayOutputStream, PrintStream}

class ControlSystemSpec extends AnyFunSpec with Matchers with BeforeAndAfter {
  private val outputStreamCaptor = new ByteArrayOutputStream()

  before {
    Console.withOut(new PrintStream(outputStreamCaptor))
  }

  after {
    Console.withOut(System.out)
    outputStreamCaptor.reset()
  }

  describe("ControlSystem") {
    it("should start the system") {
      val controlSystem = new ControlSystem()
      controlSystem.action = SleighAction.FLYING
      controlSystem.status = SleighEngineStatus.OFF
      controlSystem.startSystem()
      controlSystem.status shouldBe SleighEngineStatus.ON
      outputStreamCaptor.toString.trim shouldBe "Starting the sleigh...\nSystem ready."
    }

    it("should ascend the system") {
      val controlSystem = new ControlSystem()
      controlSystem.startSystem()
      controlSystem.ascend()
      controlSystem.action shouldBe SleighAction.FLYING
      outputStreamCaptor.toString.trim shouldBe "Starting the sleigh...\nSystem ready.\nAscending..."
    }

    it("should descend the system") {
      val controlSystem = new ControlSystem()
      controlSystem.startSystem()
      controlSystem.ascend()
      controlSystem.descend()
      controlSystem.action shouldBe SleighAction.HOVERING
      outputStreamCaptor.toString.trim shouldBe "Starting the sleigh...\nSystem ready.\nAscending...\nDescending..."
    }

    it("should park the system") {
      val controlSystem = new ControlSystem()
      controlSystem.startSystem()
      controlSystem.park()
      controlSystem.action shouldBe SleighAction.PARKED
    }
  }
}
