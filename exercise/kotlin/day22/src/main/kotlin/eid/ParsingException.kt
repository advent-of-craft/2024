package eid

data class ParsingException(override val message: String) : Exception(message)