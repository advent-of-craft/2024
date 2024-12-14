package children

import children.db2.X5T78
import children.dtos.{Address, Child, Gender}

import java.util.UUID

object ChildMapper {
  def toDto(child: X5T78): Child = {
    Child(
      id = child.id.map(UUID.fromString).getOrElse(UUID.randomUUID()),
      firstName = child.n1,
      middleName = child.n2,
      lastName = child.n3,
      birthCity = child.cityOfBirthPc,
      birthDate = child.personBd,
      gender = child.salutation match {
        case Some("Girl") => Gender.Girl
        case Some("Boy") => Gender.Boy
        case _ => throw new IllegalArgumentException("Unknown gender")
      },
      address = for {
        number <- child.stNum
        street <- child.stName
        city <- child.stC
        countryId <- child.stCid.map(id => id.toInt)
      } yield Address(number, street, city, countryId)
    )
  }
}