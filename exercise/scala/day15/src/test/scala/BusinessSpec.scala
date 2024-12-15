import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import santaChristmasList.operations.*

val factory = new Factory()
val inventory = new Inventory()
val wishList = new WishList()

val john: Child = Child("John")
val toy: Gift = Gift("Toy")
val manufacturedGift: ManufacturedGift = ManufacturedGift("123")

class BusinessSpec extends AnyFunSuite with Matchers {

  test("Gift should be loaded into Sleigh") {
    wishList.put(john, toy)
    factory.put(toy, manufacturedGift)
    inventory.put("123", toy)

    val business = new Business(factory, inventory, wishList)
    val sleigh = business.loadGiftsInSleigh(john)

    sleigh(john) should be("Gift: Toy has been loaded!")
  }

  test("Gift should not be loaded if child is not on the wish list") {
    val business = new Business(factory, inventory, wishList)
    val sleigh = business.loadGiftsInSleigh(john)

    sleigh should not contain john
  }

  test("Gift should not be loaded if the toy was not manufactured") {
    wishList.put(john, toy)

    val business = new Business(factory, inventory, wishList)
    val sleigh = business.loadGiftsInSleigh(john)

    sleigh should not contain john
  }

  test("Gift should not be loaded if the toy was misplaced") {
    wishList.put(john, toy)
    factory.put(toy, manufacturedGift)

    val business = new Business(factory, inventory, wishList)
    val sleigh = business.loadGiftsInSleigh(john)

    sleigh should not contain john
  }
}
