package domain

class Toy(val name: String, var state: Toy.State)

object Toy {
  enum State {
    case UNASSIGNED, IN_PRODUCTION, COMPLETED
  }
}