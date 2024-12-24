package usecases

import Time.*
import assertions.ToyAssertions.shouldHaveRaisedEvent
import com.github.javafaker.Faker
import domain.core.Error.anError
import domain.{StockReducedEvent, StockUnit, Toy}
import doubles.InMemoryToyRepository
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterEach, EitherValues}
import usecases.ToyDeliveryUseCase

class ToyDeliverySpec extends AnyFunSuite with Matchers with BeforeAndAfterEach with EitherValues {
  val faker: Faker = Faker()
  var toyRepository: InMemoryToyRepository = _
  var useCase: ToyDeliveryUseCase = _

  override def beforeEach(): Unit = {
    toyRepository = new InMemoryToyRepository()
    useCase = new ToyDeliveryUseCase(toyRepository)
  }

  def forASuppliedToy(stock: Int): Toy = {
    val toy = ToyBuilder.toysInStock(stock).build()
    toyRepository.save(toy)
    toy
  }

  def assertThatNoEventHasBeenRaised(): Unit =
    toyRepository.raisedEvents shouldBe empty

  test("toy and update stock") {
    val toy = forASuppliedToy(1)
    val command = DeliverToy(faker.name().fullName(), toy.getName())

    val result = useCase.handle(command)

    result.isRight shouldBe true
    toy.version() shouldBe 2
    shouldHaveRaisedEvent(
      toyRepository,
      StockReducedEvent(
        toy.id,
        now,
        command.desiredToy,
        StockUnit.from(0).getOrElse(throw new IllegalArgumentException())
      )
    )
  }

  test("toy has not been built") {
    val notBuiltToy = "Not a Bike"
    val command = DeliverToy(faker.name().fullName(), notBuiltToy)

    val result = useCase.handle(command)

    result shouldEqual Left(anError(s"Oops we have a problem... we have not built the toy: $notBuiltToy"))
    assertThatNoEventHasBeenRaised()
  }

  test("toy is not supplied anymore") {
    val toy = forASuppliedToy(0)
    val command = DeliverToy(faker.name().fullName(), toy.getName())

    val result = useCase.handle(command)

    result shouldBe Left(anError(s"No more ${toy.getName()} in stock"))
    assertThatNoEventHasBeenRaised()
  }
}