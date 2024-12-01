package doubles

import communication.Logger

class TestLogger : Logger {
    private var message: String? = null

    override fun log(message: String) {
        this.message = message
    }

    fun getLog(): String? = message
}