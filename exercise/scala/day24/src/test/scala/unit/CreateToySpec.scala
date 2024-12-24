package unit

import Time.*
import com.github.javafaker.Faker
import domain.Toy
import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}
import org.scalatest.EitherValues

object CreateToySpec extends Properties("Create Toy") with EitherValues {
  val maxToys = 1_000_000_000
  val invalidStock: Gen[Int] = Gen.chooseNum(-maxToys, -1)
  val validStock: Gen[Int] = Gen.chooseNum(0, maxToys)
  val faker: Faker = Faker()

  property("can not create toy with invalid stock (<0)") =
    forAll(invalidStock) { stock =>
      Toy.create(timeProvider, faker.funnyName().name(), stock).isLeft
    }

  property("can create toy with valid stock (>= 0)") =
    forAll(validStock) { stock =>
      Toy.create(timeProvider, faker.commerce().productName(), stock).isRight
    }
}