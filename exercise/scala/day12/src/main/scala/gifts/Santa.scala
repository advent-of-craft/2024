package gifts

import scala.collection.mutable.ListBuffer

case class Toy(description: String)

class Child(val name: String, val behavior: String) {
  private var wishlist: List[Toy] = List()

  def setWishlist(firstChoice: Toy, secondChoice: Toy, thirdChoice: Toy): Unit = {
    wishlist = List(firstChoice, secondChoice, thirdChoice)
  }

  def getWishlist: List[Toy] = wishlist
}

class Santa {
  private val childrenRepository = ListBuffer[Child]()

  def chooseToyForChild(childName: String): Option[Toy] = {
    val foundChild = childrenRepository.find(_.name == childName)

    if (foundChild.isEmpty) throw new NoSuchElementException("No such child found")

    val child = foundChild.get
    if (child.behavior == "naughty") {
      child.getWishlist.lastOption
    } else if (child.behavior == "nice") {
      child.getWishlist.lift(1)
    } else if (child.behavior == "very nice") {
      child.getWishlist.headOption
    } else {
      None
    }
  }

  def addChild(child: Child): Unit = {
    childrenRepository += child
  }
}