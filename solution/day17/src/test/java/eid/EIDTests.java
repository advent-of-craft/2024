package eid;

import io.vavr.test.Arbitrary;
import io.vavr.test.Gen;
import io.vavr.test.Property;
import org.junit.jupiter.api.Test;

import java.util.Random;

class EIDTests {
    private final Random random = new Random();
    private final Gen<Year> yearGenerator = Gen.choose(0, 99).map(Year::fromInt);
    private final Gen<Sex> sexGenerator = Gen.choose(Sex.values());
    private final Gen<SerialNumber> serialNumberGenerator = Gen.choose(1, 999).map(SerialNumber::fromInt);

    private final Arbitrary<EID> validEID =
            sexGenerator.map(EIDBuilder::new)
                    .map(eidBuilder -> eidBuilder.withYear(yearGenerator.apply(random)))
                    .map(eidBuilder -> eidBuilder.withSerialNumber(serialNumberGenerator.apply(random)))
                    .map(EIDBuilder::toEID)
                    .arbitrary();

    @Test
    void roundTrip() {
        Property.def("parseEID(eid.toString) == eid")
                .forAll(validEID)
                .suchThat(eid -> EID.parse(eid.toString()).contains(eid))
                .check()
                .assertIsSatisfied();
    }
}