package toyProduction

import domain.Toy
import domain.Toy.State.UNASSIGNED
import domain.Toy.State.IN_PRODUCTION
import doubles.InMemoryToyRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import service.ToyProductionService

class ToyProductionServiceTest : StringSpec({
    val toyName = "Train"

    "assignToyToElfShouldPassTheItemInProduction" {
        val repository = InMemoryToyRepository()
        val service = ToyProductionService(repository)
        repository.save(Toy(toyName, UNASSIGNED))

        service.assignToyToElf(toyName)

        repository.findByName(toyName)?.state shouldBe IN_PRODUCTION
    }
})
