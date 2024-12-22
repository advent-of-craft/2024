import eid.{EID, SerialNumber, Sex, Year}
import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Gen.{choose, oneOf}

object Generators {
  val validEID: Gen[EID] =
    Arbitrary {
      for {
        sex <- oneOf(Sex.values)
        year <- choose(0, 99)
        serialNumber <- choose(1, 999)
      } yield new EID(
        sex,
        Year(year),
        SerialNumber(serialNumber)
      )
    }.arbitrary
}