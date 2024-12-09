package gift

data class GiftRequest(
    val giftName: String,
    val isFeasible: Boolean,
    val priority: Priority
)