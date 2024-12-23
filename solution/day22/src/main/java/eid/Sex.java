package eid;

import io.vavr.control.Either;

import static io.vavr.API.*;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public enum Sex {
    Sloubi('1'),
    Gagna('2'),
    Catact('3');

    public final char value;

    Sex(char value) {
        this.value = value;
    }

    public static Either<ParsingError, Sex> parseSex(char potentialSex) {
        // vavr Pattern matching
        return Match(potentialSex).of(
                Case($('1'), right(Sloubi)),
                Case($('2'), right(Gagna)),
                Case($('3'), right(Catact)),
                Case($(), left((new ParsingError("Not a valid sex"))))
        );
    }

    @Override
    public String toString() {
        return "" + value;
    }
}