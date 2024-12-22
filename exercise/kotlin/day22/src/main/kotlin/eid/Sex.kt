package eid

enum class Sex {
    Sloubi, Gagna, Catact;

    override fun toString(): String =
        when (this) {
            Sloubi -> "1"
            Gagna -> "2"
            Catact -> "3"
        }

    companion object {
        fun parse(potentialSex: Char): Sex? =
            when (potentialSex) {
                '1' -> Sloubi
                '2' -> Gagna
                '3' -> Catact
                else -> null
            }
    }
}
