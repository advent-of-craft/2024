package christmas

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class PreparationTests : StringSpec({
    "prepareGifts should return the correct preparation message" {
        forAll(
            row(-1, "No gifts to prepare."),
            row(0, "No gifts to prepare."),
            row(1, "Elves will prepare the gifts."),
            row(49, "Elves will prepare the gifts."),
            row(50, "Santa will prepare the gifts.")
        ) { numberOfGifts, expected ->
            Preparation.prepareGifts(numberOfGifts) shouldBe expected
        }
    }

    "categorizeGift should return the correct category based on age" {
        forAll(
            row(1, "Baby"),
            row(3, "Toddler"),
            row(6, "Child"),
            row(13, "Teen")
        ) { age, expectedCategory ->
            Preparation.categorizeGift(age) shouldBe expectedCategory
        }
    }

    "ensureToyBalance should return the correct balance check" {
        forAll(
            row(ToyType.EDUCATIONAL, 25, 100, true),
            row(ToyType.FUN, 30, 100, true),
            row(ToyType.CREATIVE, 20, 100, true),
            row(ToyType.EDUCATIONAL, 20, 100, false),
            row(ToyType.FUN, 29, 100, false),
            row(ToyType.CREATIVE, 15, 100, false)
        ) { toyType, toysCount, totalToys, expected ->
            Preparation.ensureToyBalance(toyType, toysCount, totalToys) shouldBe expected
        }
    }
})
