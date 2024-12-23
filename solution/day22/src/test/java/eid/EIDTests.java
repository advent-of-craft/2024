package eid;

import io.vavr.Function1;
import io.vavr.test.Arbitrary;
import io.vavr.test.Gen;
import io.vavr.test.Property;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static io.vavr.Tuple.of;
import static io.vavr.test.Gen.choose;
import static io.vavr.test.Gen.frequency;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static org.assertj.core.util.Strings.concat;

class EIDTests {
    private static final Random random = new Random();
    private final Gen<Year> yearGenerator = choose(0, 99).map(Year::fromInt);
    private final Gen<Sex> sexGenerator = choose(Sex.values());
    private final Gen<SerialNumber> serialNumberGenerator = choose(1, 999).map(SerialNumber::fromInt);
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

    private record Mutator(String name, Function1<EID, Gen<String>> func) {
        public String mutate(EID eid) {
            return func.apply(eid).apply(random);
        }
    }

    private static final Mutator truncateMutator = new Mutator("Truncate mutator", eid ->
            choose(1, 8).map(size ->
                    size == 1 ? "" : eid.toString().substring(0, size - 1)
            )
    );

    private static final Mutator invalidCharacterMutator = new Mutator("Invalid character mutator", eid ->
            choose(0, 7).map(index -> {
                        char[] chars = eid.toString().toCharArray();
                        chars[index] = (char) (random.nextInt(26) + 'a');

                        return new String(chars);
                    })
    );


    private static final Mutator sexMutator = new Mutator("Sex mutator", eid ->
            choose(4, 9)
                    .map(invalidSex -> invalidSex + eid.toString().substring(1))
    );
    private static final Mutator yearMutator = new Mutator("Year mutator", eid ->
            frequency(of(7, choose(100, 999)), of(3, choose(1, 9)))
                    .map(invalidYear ->
                            concat(
                                    eid.toString().charAt(0),
                                    invalidYear.toString(),
                                    eid.toString().substring(3)
                            )
                    )
    );

    private static final Mutator serialNumberMutator = new Mutator("Serial Number mutator", eid ->
            frequency(of(7, choose(1000, 9999)), of(3, choose(1, 99)))
                    .map(invalidSerialNumber ->
                            concat(
                                    eid.toString().substring(0, 3),
                                    invalidSerialNumber.toString(),
                                    eid.toString().substring(6))
                    )
    );

    private static final Mutator keyMutator = new Mutator("Key mutator", eid ->
            choose(0, 97)
                    .filter(x -> x != parseInt(eid.toString().substring(6)))
                    .map(invalidKey -> concat(
                            eid.toString().substring(0, 6),
                            format("%02d", invalidKey)
                    ))
    );

    private static final Arbitrary<Mutator> mutators = choose(
            truncateMutator,
            invalidCharacterMutator,
            sexMutator,
            yearMutator,
            serialNumberMutator,
            keyMutator
    ).arbitrary();

    @Test
    void invalidEIDsCanNotBeParsed() {
        Property.def("mutate(eid.toString) == error")
                .forAll(validEID, mutators)
                .suchThat((eid, mutator) -> {
                    System.out.println(mutator.name + ":" + mutator.mutate(eid));
                    return EID.parse(mutator.mutate(eid)).isLeft();
                })
                .check()
                .assertIsSatisfied();
    }
}