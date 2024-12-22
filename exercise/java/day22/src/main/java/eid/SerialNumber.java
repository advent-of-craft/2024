package eid;

import io.vavr.control.Either;
import lombok.EqualsAndHashCode;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod(StringExtensions.class)
@EqualsAndHashCode
public class SerialNumber {
    private final int value;

    private SerialNumber(int value) {
        this.value = value;
    }

    public static SerialNumber fromInt(int potentialSerialNumber) {
        return new SerialNumber(potentialSerialNumber);
    }

    public static Either<ParsingError, SerialNumber> parseSerialNumber(String potentialSerialNumber) {
        return potentialSerialNumber
                .toInt()
                .filter(x -> x >= 1 && x <= 999)
                .map(SerialNumber::new)
                .toEither(new ParsingError("serial number should be between 1 and 999"));
    }

    @Override
    public String toString() {
        return String.format("%03d", value);
    }
}