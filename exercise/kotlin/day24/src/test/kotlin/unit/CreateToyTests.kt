package unit

import Time
import com.github.javafaker.Faker
import domain.Toy
import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll

class CreateToyTests : StringSpec({
    val maxToys = 1_000_000_000
    val invalidStock = Arb.int(-maxToys, -1)
    val validStock = Arb.int(0, maxToys)
    val faker = Faker()

    "can not create toy with invalid stock (<0)" {
        checkAll(invalidStock) { stock ->
            Toy.create(Time.TimeProvider, "", stock).isLeft()
        }
    }

    "can create toy with valid stock (>= 0)" {
        checkAll(validStock) { stock ->
            Toy.create(Time.TimeProvider, faker.funnyName().name(), stock).isRight()
        }
    }
})
