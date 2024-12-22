package eid;

import io.vavr.control.Either;
import lombok.EqualsAndHashCode;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod(StringExtensions.class)
@EqualsAndHashCode
public class Year {
    private final int value;

    private Year(int value) {
        this.value = value;
    }

    public static Year fromInt(int potentialYear) {
        return new Year(potentialYear);
    }

    public static Either<ParsingError, Year> parseYear(String potentialYear) {
        return potentialYear
                .toInt()
                .filter(x -> x >= 0 && x <= 99)
                .map(Year::new)
                .toEither(new ParsingError("year should be between 0 and 99"));
    }

    @Override
    public String toString() {
        return String.format("%02d", value);
    }
}