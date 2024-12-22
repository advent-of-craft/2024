package eid.tests

import eid.EID
import eid.SerialNumber
import eid.Sex
import eid.Year
import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.enum
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.of
import io.kotest.property.forAll

val eidGenerator = arbitrary { _ ->
    EID(
        sex = Arb.enum<Sex>().bind(),
        year = Year(Arb.int(0, 99).bind()),
        serialNumber = SerialNumber(Arb.int(1, 999).bind())
    )
}

class EIDProperties : StringSpec({
    "EID round tripping" {
        forAll(eidGenerator) { validEID ->
            EID.parse(validEID.toString()).getOrNull() == validEID
        }
    }

    "Invalid EID can never be parsed" {
        forAll(eidGenerator, mutantGenerator) { validEID, mutator ->
            collect(mutator.name)
            EID.parse(mutator.mutate(validEID)).isFailure
        }
    }
})

private data class Mutator(val name: String, val func: (EID) -> Arb<String>) {
    fun mutate(eid: EID): String = func(eid).samples().first().value
}

private val aMutator: Mutator = Mutator("a mutator") { eid ->
    Arb.of("Implement this first mutator")
}

private val mutantGenerator: Arb<Mutator> = Arb.of(
    aMutator
)