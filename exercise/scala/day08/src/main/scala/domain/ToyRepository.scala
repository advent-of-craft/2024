package domain

trait ToyRepository {
  def findByName(name: String): Option[Toy]

  def save(toy: Toy): Unit
}