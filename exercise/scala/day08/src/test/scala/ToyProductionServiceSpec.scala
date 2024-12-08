import domain.Toy
import domain.Toy.State.*
import doubles.InMemoryToyRepository
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import service.ToyProductionService

class ToyProductionServiceSpec extends AnyFunSuite with Matchers {
  private val toyName = "Train"

  test("assignToyToElf should pass the item in production") {
    val repository = new InMemoryToyRepository()
    val service = new ToyProductionService(repository)
    repository.save(new Toy(toyName, UNASSIGNED))

    service.assignToyToElf(toyName)

    repository.findByName(toyName).map(_.state) shouldBe Some(IN_PRODUCTION)
  }
}
