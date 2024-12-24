package domain.core

data class Error(val message: String) {
    companion object {
        fun anError(message: String) = Error(message)
    }
}