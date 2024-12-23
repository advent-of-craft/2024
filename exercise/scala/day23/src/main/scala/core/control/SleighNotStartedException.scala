package core.control

class SleighNotStartedException extends Throwable {
  override def getMessage: String = "The sleigh is not started. Please start the sleigh before any other action..."
}