package eid

opaque type ParsingError = String

object ParsingError:
  def apply(error: String): ParsingError = error