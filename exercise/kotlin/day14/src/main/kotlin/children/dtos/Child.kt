package children.dtos

import java.util.*

data class Child(
    var id: UUID,
    var firstName: String?,
    var middleName: String?,
    var lastName: String?,
    var birthCity: String?,
    var birthDate: String?,
    var gender: Gender,
    var address: Address?
)

enum class Gender {
    Girl,
    Boy
}

data class Address(
    var number: String,
    var street: String,
    var city: String,
    var countryId: Int
)