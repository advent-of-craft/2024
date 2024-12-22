package eid


extension (input: String)
  def toIntEither: Either[String, Int] = input.toIntOption.toRight("Not a valid number")

  def toEitherParsing[T](isValid: (x: Int) => Boolean, parsingErrorMessage: String): Either[ParsingError, Int] =
    input.toIntEither match {
      case Right(x) if isValid(x) => Right(x)
      case _ => Left(ParsingError(parsingErrorMessage))
    }
