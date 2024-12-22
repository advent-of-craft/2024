package eid

import scala.util.{Either, Left, Right}

case class EID(sex: Sex, year: Year, serialNumber: SerialNumber) {
  override def toString: String = s"$sex${f"$year%02d"}${f"$serialNumber%03d"}${String.format("%02d", key())}"

  def key(): Int = (97 - (toLong % 97)).toInt

  private def toLong: Long = (sex.toString + year.toString + serialNumber.toString).toLong
}

object EID {
  def parse(potentialEID: String): Either[ParsingError, EID] =
    (for {
      sex <- Sex.parse(potentialEID.head)
      year <- Year.parse(potentialEID.substring(1, 3))
      serialNumber <- SerialNumber.parse(potentialEID.substring(3, 6))
      eid = EID(sex, year, serialNumber)
    } yield eid)
      .flatMap(result => checkKey(potentialEID.substring(6), result))

  private def checkKey(input: String, eid: EID): Either[ParsingError, EID] =
    input.toIntOption match {
      case Some(key) if key == eid.key() => Right(eid)
      case _ => Left(ParsingError("Invalid key"))
    }
}