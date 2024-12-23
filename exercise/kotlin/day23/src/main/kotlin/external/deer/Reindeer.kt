package external.deer

class Reindeer(
    private val name: String,
    private val age: Int,
    private val spirit: Int,
    var sick: Boolean = false
) {
    var timesHarnessing: Int = 0
    private val powerPullLimit: Int = if (age <= 5) 5 else 5 - (age - 5)

    val magicPower: Float
        get() = if (!sick || needsRest()) {
            when {
                age == 1 -> spirit * 0.5f
                age <= 5 -> spirit.toFloat()
                else -> spirit * 0.25f
            }
        } else {
            0f
        }

    fun needsRest() = if (!sick) timesHarnessing == powerPullLimit else true
}
