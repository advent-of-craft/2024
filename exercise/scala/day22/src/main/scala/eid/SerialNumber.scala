package eid

opaque type SerialNumber = Int

object SerialNumber {
  private val parsingErrorMessage = "Serial number should be between 1 and 999"

  def parse(input: String): Either[ParsingError, SerialNumber] =
    input.toEitherParsing(x => isValid(x), parsingErrorMessage)

  def apply(value: Int): SerialNumber = {
    require(isValid(value), parsingErrorMessage)
    value
  }

  private def isValid(value: Int): Boolean = value >= 1 && value <= 999
}