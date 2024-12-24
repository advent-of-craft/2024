package domain

import domain.core.Repository

trait ToyRepository extends Repository[Toy] {
  def findByName(toyName: String): Option[Toy]
}