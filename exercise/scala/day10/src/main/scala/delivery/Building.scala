package delivery

object Building {
  def whichFloor(instructions: String): Int = {
    val valList = scala.collection.mutable.ListBuffer[(Char, Int)]()
    var result = 0

    for (i <- instructions.indices) {
      val c = instructions(i)

      if (instructions.contains("🧝")) {
        val j = if (c == ')') 3 else -2
        valList += ((c, j))
      } else if (!instructions.contains("🧝")) {
        valList += ((c, if (c == '(') 1 else -1))
      } else {
        valList += ((c, if (c == '(') 42 else -2))
      }
    }

    for (kp <- valList) {
      result += kp._2
    }

    result
  }
}