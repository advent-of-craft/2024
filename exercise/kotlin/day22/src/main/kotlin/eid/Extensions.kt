package eid

fun <T> String.parseOrNull(
    isValid: (Int) -> Boolean,
    factory: (Int) -> T
): T? = this.toIntOrNull()?.let { if (isValid(it)) factory(it) else null }

fun Int.toString2(): String = String.format("%02d", this)
fun Int.toString3(): String = String.format("%03d", this)