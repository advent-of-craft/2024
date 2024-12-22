package eid

@JvmInline
value class SerialNumber(private val value: Int) {
    init {
        require(value.isValid())
    }

    override fun toString(): String = value.toString3()

    companion object Parser {
        private fun Int.isValid() = this in 1..999
        fun parse(input: String): SerialNumber? = input.parseOrNull({ it.isValid() }, ::SerialNumber)
    }
}