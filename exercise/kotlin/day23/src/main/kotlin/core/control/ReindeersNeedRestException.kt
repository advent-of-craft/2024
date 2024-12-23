package core.control

class ReindeersNeedRestException : Throwable() {
    override val message: String
        get() = "The reindeer needs rest. Please park the sleigh..."
}