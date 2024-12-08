package doubles

import domain.{Toy, ToyRepository}

class InMemoryToyRepository extends ToyRepository {
  private val toys = scala.collection.mutable.ListBuffer[Toy]()

  override def findByName(name: String): Option[Toy] = toys.find(_.name == name)

  override def save(toy: Toy): Unit = {
    toys -= toy
    toys += toy
  }
}