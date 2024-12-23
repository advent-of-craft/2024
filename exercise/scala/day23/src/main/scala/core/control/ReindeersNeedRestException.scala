package core.control

class ReindeersNeedRestException extends Throwable {
  override def getMessage: String = "The reindeer needs rest. Please park the sleigh..."
}
