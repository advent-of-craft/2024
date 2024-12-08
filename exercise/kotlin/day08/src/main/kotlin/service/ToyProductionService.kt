package service

import domain.Toy
import domain.ToyRepository

class ToyProductionService(private val repository: ToyRepository) {
    fun assignToyToElf(toyName: String) {
        val toy = repository.findByName(toyName)
        if (toy != null && toy.state == Toy.State.UNASSIGNED) {
            toy.state = Toy.State.IN_PRODUCTION
            repository.save(toy)
        }
    }
}
