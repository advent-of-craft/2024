package eid

import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

data class EID(val sex: Sex, val year: Year, val serialNumber: SerialNumber) {
    companion object {
        fun parse(potentialEID: String): Result<EID> {
            EID(
                sex = Sex.parse(potentialEID[0]) ?: return "Not a valid sex".toFailure(),
                year = Year.parse(potentialEID.substring(1).take(2))
                    ?: return "Year should be positive and lt 100".toFailure(),
                serialNumber = SerialNumber.parse(potentialEID.substring(3).take(3))
                    ?: return "Serial number should be gt 0 and lt 1000".toFailure()
            ).let {
                return if (it.checkKey(potentialEID.substring(6))) success(it)
                else "Invalid key".toFailure()
            }
        }

        private fun String.toFailure(): Result<EID> = failure(ParsingException(this))

        private fun EID.checkKey(potentialKey: String): Boolean =
            potentialKey
                .toIntOrNull()
                .let { return this.key() == it }
    }

    private fun stringWithoutKey(): String = "${sex}${year}${serialNumber}"
    private fun toLong(): Long = (sex.toString() + year + serialNumber).toLong()

    fun key(): Int = (97 - (toLong() % 97)).toInt()

    override fun toString(): String = stringWithoutKey() + String.format("%02d", key())
}
