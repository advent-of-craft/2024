package children

import children.db2.X5T78
import children.dtos.Address
import children.dtos.Gender
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.*

class ChildMapperTest : StringSpec({
    val mapper = ChildMapper.INSTANCE

    "should map X5T78 to Child for a girl" {
        val db2Child = X5T78(
            id = UUID.randomUUID().toString(),
            n1 = "Alice",
            n2 = "Marie",
            n3 = "Smith",
            cityOfBirthPc = "Paradise",
            personBd = "19/03/2017",
            salutation = "Girl",
            stNum = "123",
            stName = "Sunny Street",
            stC = "Paradise",
            stCid = "99"
        )

        val child = mapper.toDto(db2Child)

        child.id shouldBe UUID.fromString(db2Child.id)
        child.firstName shouldBe db2Child.n1
        child.middleName shouldBe db2Child.n2
        child.lastName shouldBe db2Child.n3
        child.birthCity shouldBe db2Child.cityOfBirthPc
        child.birthDate shouldBe db2Child.personBd
        child.gender shouldBe Gender.Girl
        child.address shouldBe Address("123", "Sunny Street", "Paradise", 99)
    }

    "should map X5T78 to Child for a boy" {
        val db2Child = X5T78(
            id = UUID.randomUUID().toString(),
            n1 = "Bob",
            n3 = "Brown",
            cityOfBirthPc = "Paradise",
            personBd = "01/09/2021",
            salutation = "Boy",
            stNum = "9",
            stName = "Oak Street",
            stC = "Paradise",
            stCid = "98988"
        )

        val child = mapper.toDto(db2Child)

        child.id shouldBe UUID.fromString(db2Child.id)
        child.firstName shouldBe db2Child.n1
        child.middleName shouldBe db2Child.n2
        child.lastName shouldBe db2Child.n3
        child.birthCity shouldBe db2Child.cityOfBirthPc
        child.birthDate shouldBe db2Child.personBd
        child.gender shouldBe Gender.Boy
        child.address shouldBe Address("9", "Oak Street", "Paradise", 98988)
    }
})