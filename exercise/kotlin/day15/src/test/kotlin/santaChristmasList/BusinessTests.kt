package santaChristmasList

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.maps.shouldNotContainKey
import io.kotest.matchers.shouldBe
import santaChristmasList.operations.*

class BusinessTests : StringSpec({
    val barCode = "123"

    lateinit var factory: Factory
    lateinit var inventory: Inventory
    lateinit var wishList: WishList

    val john = Child("John")
    val toy = Gift("Toy")
    val manufacturedGift = ManufacturedGift(barCode)

    beforeTest {
        factory = Factory()
        inventory = Inventory()
        wishList = WishList()
    }

    "Gift should be loaded into Sleigh" {
        wishList[john] = toy
        factory[toy] = manufacturedGift
        inventory[barCode] = toy

        val business = Business(factory, inventory, wishList)
        val sleigh = business.loadGiftsInSleigh(john)

        sleigh[john] shouldBe "Gift: Toy has been loaded!"
    }

    "Gift should not be loaded given child is not on wish list" {
        val business = Business(factory, inventory, wishList)
        val sleigh = business.loadGiftsInSleigh(john)

        sleigh shouldNotContainKey john
    }

    "Gift should not be loaded given toy was not manufactured" {
        wishList[john] = toy

        val business = Business(factory, inventory, wishList)
        val sleigh = business.loadGiftsInSleigh(john)

        sleigh shouldNotContainKey john
    }

    "Gift should not be loaded given toy was misplaced" {
        wishList[john] = toy
        factory[toy] = manufacturedGift

        val business = Business(factory, inventory, wishList)
        val sleigh = business.loadGiftsInSleigh(john)

        sleigh shouldNotContainKey john
    }
})
