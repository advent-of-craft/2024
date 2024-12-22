import Generators.validEID
import Mutators.mutantGenerator
import eid.EID
import org.scalacheck.Prop.{classify, forAll}
import org.scalacheck.{Gen, Properties}
import org.scalatest.EitherValues

case class Mutator(name: String, func: EID => Gen[String]) {
  def mutate(eid: EID): String = func(eid).sample.get
}

object EIDProperties extends Properties("EID") with EitherValues {

  property("round tripping") = forAll(validEID) { eid =>
    EID.parse(eid.toString).value == eid
  }

  property("Invalid EID can never be parsed") = forAll(validEID, mutantGenerator) { (eid, mutator) =>
    classify(true, mutator.name) {
      EID.parse(mutator.mutate(eid)).isLeft
    }
  }
}

object Mutators {
  val aMutator: Mutator = Mutator("a mutator", eid => Gen.const("Implement this first mutator"))

  val mutantGenerator: Gen[Mutator] = Gen.oneOf(
    aMutator,
    aMutator
  )
}