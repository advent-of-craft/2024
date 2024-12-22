package eid

@JvmInline
value class Year(private val value: Int) {
    init {
        require(value.isValid())
    }

    override fun toString(): String = value.toString2()

    companion object Parser {
        private fun Int.isValid() = this in 0..99
        fun parse(input: String): Year? = input.parseOrNull({ it.isValid() }, ::Year)
    }
}