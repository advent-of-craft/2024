import gift.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.booleans.shouldBeFalse

class SantaServiceTests : StringSpec({
    val service = SantaService()

    "request is approved for nice child with feasible gift" {
        val niceChild = Child("Alice", "Thomas", Behavior.NICE, GiftRequest("Bicycle", true, Priority.NICE_TO_HAVE))
        service.evaluateRequest(niceChild).shouldBeTrue()
    }

    "request is denied for naughty child" {
        val naughtyChild = Child("Noa", "Thierry", Behavior.NAUGHTY, GiftRequest("SomeToy", true, Priority.DREAM))
        service.evaluateRequest(naughtyChild).shouldBeFalse()
    }

    "request is denied for nice child with infeasible gift" {
        val niceChildWithInfeasibleGift = Child("Charlie", "Joie", Behavior.NICE, GiftRequest("AnotherToy", false, Priority.DREAM))
        service.evaluateRequest(niceChildWithInfeasibleGift).shouldBeFalse()
    }
})
