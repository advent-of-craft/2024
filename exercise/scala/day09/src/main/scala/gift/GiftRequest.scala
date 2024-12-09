package gift

case class GiftRequest(
                        giftName: String,
                        isFeasible: Boolean,
                        priority: Priority
                      )