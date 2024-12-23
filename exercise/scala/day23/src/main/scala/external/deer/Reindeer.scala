package external.deer

class Reindeer(val name: String, val age: Int, private val spirit: Int, var sick: Boolean = false) {
  private val powerPullLimit: Int = if (age <= 5) 5 else 5 - (age - 5)
  var timesHarnessing: Int = 0

  def getMagicPower: Float = {
    if (!sick || needsRest()) {
      age match {
        case 1 => spirit * 0.5f
        case a if a <= 5 => spirit.toFloat
        case _ => spirit * 0.25f
      }
    } else 0f
  }

  def needsRest(): Boolean = if (!sick) timesHarnessing == powerPullLimit else true
}