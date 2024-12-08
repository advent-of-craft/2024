import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should
import production.Gift
import production.Status.Produced
import production.Workshop

private const val toyName = "1 Super Nintendo"

class WorkshopTest : StringSpec({
    val workshop = Workshop()

    "completing a gift should set its status to produced" {
        workshop.addGift(Gift(toyName))
            .let { workshop.completeGift(toyName)!! } should beCompleted()
    }

    "completing a non existing gift should return nothing" {
        workshop.completeGift("UnExisting toy") should beEmpty()
    }
})

fun beCompleted() = Matcher<Gift> { value ->
    MatcherResult(
        value.status == Produced,
        { "Gift ${value.name} should be completed" },
        { "Gift ${value.name} should not be completed" },
    )
}

fun beEmpty() = Matcher<Gift?> { value ->
    MatcherResult(
        value == null,
        { "Gift should be empty" },
        { "Gift should should not be filled" },
    )
}