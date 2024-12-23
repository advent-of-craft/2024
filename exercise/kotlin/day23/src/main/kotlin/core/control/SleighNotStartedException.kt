package core.control

class SleighNotStartedException : Throwable() {
    override val message: String
        get() = "The sleigh is not started. Please start the sleigh before any other action..."
}