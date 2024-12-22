package eid

opaque type Year = Int

object Year {
  private val parsingErrorMessage = "Year should be between 0 and 99"

  def parse(input: String): Either[ParsingError, Year] =
    input.toEitherParsing(x => isValid(x), parsingErrorMessage)

  def apply(value: Int): Year = {
    require(isValid(value), parsingErrorMessage)
    value
  }

  private def isValid(value: Int): Boolean = value >= 0 && value <= 99
}