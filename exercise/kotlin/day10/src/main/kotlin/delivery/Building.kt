package delivery

object Building {
    fun whichFloor(instructions: String): Int {
        val valList = mutableListOf<Pair<Char, Int>>()
        var result = 0

        for (i in instructions.indices) {
            val c = instructions[i]

            if (instructions.contains("🧝")) {
                val j = if (c == ')') 3 else -2
                valList.add(Pair(c, j))
            } else if (!instructions.contains("🧝")) {
                valList.add(Pair(c, if (c == '(') 1 else -1))
            } else {
                valList.add(Pair(c, if (c == '(') 42 else -2))
            }
        }

        for (kp in valList) {
            result += kp.second
        }

        return result
    }
}
