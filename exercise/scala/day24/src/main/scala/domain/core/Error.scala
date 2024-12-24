package domain.core

case class Error(message: String)

object Error {
  def anError(message: String): Error = Error(message)
}