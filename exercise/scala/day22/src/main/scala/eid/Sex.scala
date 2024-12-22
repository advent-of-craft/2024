package eid

enum Sex(val symbol: Char) {
  case Sloubi extends Sex('1')
  case Gagna extends Sex('2')
  case Catact extends Sex('3')

  override def toString: String = symbol.toString
}

object Sex {
  def parse(potentialSex: Char): Either[ParsingError, Sex] = Sex.values
    .find(_.symbol == potentialSex)
    .toRight(ParsingError("Not a valid sex"))
}