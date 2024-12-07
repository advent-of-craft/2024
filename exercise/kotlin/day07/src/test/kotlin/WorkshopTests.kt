import io.kotest.core.spec.style.StringSpec
import production.Gift
import production.Status
import production.Workshop
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

private const val toyName = "1 Super Nintendo"

class WorkshopTest : StringSpec({
    "completing a gift should set its status to produced" {
        Workshop()
            .let { workshop ->
                workshop.addGift(Gift(toyName))

                val completedGift = workshop.completeGift(toyName)

                assertNotNull(completedGift)
                assertEquals(Status.Produced, completedGift.status)
            }
    }

    "completing a non existing gift should return nothing" {
        Workshop()
            .let { workshop ->
                val completedGift = workshop.completeGift("UnExisting toy")
                assertEquals(null, completedGift)
            }
    }
})