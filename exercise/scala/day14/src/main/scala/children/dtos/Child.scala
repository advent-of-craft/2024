package children.dtos

import java.util.UUID

case class Child(
                  var id: UUID,
                  var firstName: Option[String],
                  var middleName: Option[String],
                  var lastName: Option[String],
                  var birthCity: Option[String],
                  var birthDate: Option[String],
                  var gender: Gender,
                  var address: Option[Address]
                )

enum Gender {
  case Girl, Boy
}

case class Address(
                    var number: String,
                    var street: String,
                    var city: String,
                    var countryId: Int
                  )
