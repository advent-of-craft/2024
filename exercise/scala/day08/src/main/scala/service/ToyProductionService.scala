package service

import domain.Toy.State.*
import domain.{Toy, ToyRepository}

class ToyProductionService(private val repository: ToyRepository) {
  def assignToyToElf(toyName: String): Unit = {
    val toyOpt = repository.findByName(toyName)
    toyOpt.foreach { toy =>
      if (toy.state == UNASSIGNED) {
        toy.state = IN_PRODUCTION
        repository.save(toy)
      }
    }
  }
}