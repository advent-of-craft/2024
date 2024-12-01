package doubles

import communication.Logger

class TestLogger extends Logger {
  private var message: Option[String] = None

  override def log(message: String): Unit = {
    this.message = Some(message)
  }

  def getLog: Option[String] = message
}