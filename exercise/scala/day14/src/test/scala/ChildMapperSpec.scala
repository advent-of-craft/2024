import children.ChildMapper
import children.db2.X5T78
import children.dtos.{Address, Gender}
import org.scalatest.OptionValues
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import java.util.UUID

class ChildMapperSpec extends AnyFunSuite with Matchers with OptionValues {
  test("should map X5T78 to Child for a girl") {
    val db2Child = X5T78(
      id = Some(UUID.randomUUID().toString),
      n1 = Some("Alice"),
      n2 = Some("Marie"),
      n3 = Some("Smith"),
      cityOfBirthPc = Some("Paradise"),
      personBd = Some("19/03/2017"),
      salutation = Some("Girl"),
      stNum = Some("123"),
      stName = Some("Sunny Street"),
      stC = Some("Paradise"),
      stCid = Some("99")
    )

    val child = ChildMapper.toDto(db2Child)

    child.id shouldBe UUID.fromString(db2Child.id.get)
    child.firstName should contain(db2Child.n1.value)
    child.middleName should contain(db2Child.n2.value)
    child.lastName should contain(db2Child.n3.value)
    child.birthCity should contain(db2Child.cityOfBirthPc.value)
    child.birthDate should contain(db2Child.personBd.value)
    child.gender shouldBe Gender.Girl
    child.address shouldBe Some(Address("123", "Sunny Street", "Paradise", 99))
  }

  test("should map X5T78 to Child for a boy") {
    val db2Child = X5T78(
      id = Some(UUID.randomUUID().toString),
      n1 = Some("Bob"),
      n3 = Some("Brown"),
      cityOfBirthPc = Some("Paradise"),
      personBd = Some("01/09/2021"),
      salutation = Some("Boy"),
      stNum = Some("9"),
      stName = Some("Oak Street"),
      stC = Some("Paradise"),
      stCid = Some("98988")
    )

    val child = ChildMapper.toDto(db2Child)

    child.id shouldBe UUID.fromString(db2Child.id.get)
    child.firstName.value shouldBe db2Child.n1.value
    child.lastName.value shouldBe db2Child.n3.value
    child.birthCity.value shouldBe db2Child.cityOfBirthPc.value
    child.birthDate.value shouldBe db2Child.personBd.value
    child.gender shouldBe Gender.Boy
    child.address shouldBe Some(Address("9", "Oak Street", "Paradise", 98988))
  }
}
