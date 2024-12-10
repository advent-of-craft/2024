import delivery.Building
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

fun loadInstructionsFromFile(input: String): String = Files.readString(
    Path.of(Objects.requireNonNull(DeliveryShould::class.java.getClassLoader().getResource(input)).toURI())
)

class DeliveryShould : StringSpec({
    "return floor number based on instructions" {
        forAll(
            row("1", 0),
            row("2", 3),
            row("3", -1),
            row("4", 53),
            row("5", -3),
            row("6", 2920)
        ) { fileName, expectedFloor ->
            loadInstructionsFromFile("$fileName.txt")
                .let { instructions ->
                    Building.whichFloor(instructions) shouldBe expectedFloor
                }
        }
    }
})