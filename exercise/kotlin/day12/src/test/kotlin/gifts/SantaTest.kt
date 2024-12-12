package gifts

import io.kotest.core.spec.style.StringSpec
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private val playstation = Toy("playstation")
private val ball = Toy("ball")
private val  plush = Toy("plush")

class SantaTest : StringSpec ({

    "naughty child should receive last choice" {
        val bobby = Child("bobby", "naughty")
        bobby.setWishList(playstation, plush, ball)
        val santa = Santa()
        santa.addChild(bobby)
        val receivedToy = santa.chooseToyForChild("bobby")
        assertEquals(ball, receivedToy)
    }

    "very nice child should receive first choice" {
        val bobby = Child("bobby", "very nice")
        bobby.setWishList(playstation, plush, ball)
        val santa = Santa()
        santa.addChild(bobby)
        val receivedToy = santa.chooseToyForChild("bobby")
        assertEquals(playstation, receivedToy)
    }

    "nice child should receive second choice" {
        val bobby = Child("bobby", "nice")
        bobby.setWishList(playstation, plush, ball)
        val santa = Santa()
        santa.addChild(bobby)
        val receivedToy = santa.chooseToyForChild("bobby")
        assertEquals(plush, receivedToy)
    }

    "no existing child should throw exception" {
        val santa = Santa()
        val bobby = Child("bobby", "very nice")
        bobby.setWishList(playstation, plush, ball)
        santa.addChild(bobby)

        assertFailsWith<NoSuchElementException> { santa.chooseToyForChild("alice") }
    }

})