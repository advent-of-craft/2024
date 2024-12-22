package eid;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.ExtensionMethod;

import java.util.Objects;

import static eid.Sex.parseSex;
import static java.lang.String.format;

@AllArgsConstructor
@EqualsAndHashCode
@ExtensionMethod(StringExtensions.class)
public class EID {
    private final Sex sex;
    private final Year year;
    private final SerialNumber serialNumber;

    public static Either<ParsingError, EID> parse(String potentialEID) {
        return parseSex(potentialEID.charAt(0))
                .map(EIDBuilder::new)
                .flatMap(eidBuilder -> parseYear(potentialEID.substring(1, 3), eidBuilder))
                .flatMap(eidBuilder -> parseSerialNumber(potentialEID.substring(3, 6), eidBuilder))
                .map(eidBuilder -> new EID(eidBuilder.getSex(), eidBuilder.getYear(), eidBuilder.getSerialNumber()))
                .flatMap(eid -> checkKey(potentialEID.substring(6), eid));
    }

    private static Either<ParsingError, EIDBuilder> parseYear(String potentialYear, EIDBuilder builder) {
        return Year.parseYear(potentialYear)
                .map(builder::withYear);
    }

    private static Either<ParsingError, EIDBuilder> parseSerialNumber(String potentialSerialNumber, EIDBuilder builder) {
        return SerialNumber.parseSerialNumber(potentialSerialNumber)
                .map(builder::withSerialNumber);
    }

    private static Either<ParsingError, EID> checkKey(String potentialKey, EID eid) {
        return StringExtensions.toInt(potentialKey)
                .filter(parsedKey -> Objects.equals(eid.key(), parsedKey))
                .map(x -> eid)
                .toEither(new ParsingError("invalid key"));
    }

    @Override
    public String toString() {
        return stringWithoutKey() + format("%02d", key());
    }

    private String stringWithoutKey() {
        return sex.toString() + year + serialNumber;
    }

    private Integer key() {
        return stringWithoutKey()
                .toInt()
                .map(x -> (97 - (x % 97)))
                .get();
    }
}