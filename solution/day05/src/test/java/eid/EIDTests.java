package eid;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class EIDTests {
    public static Stream<Arguments> invalidEIDs() {
        return Stream.of(
                Arguments.of(null, "input can not be null"),
                Arguments.of("", "empty string"),
                Arguments.of("2230", "too short"),
                Arguments.of("40000325", "incorrect sex"),
                Arguments.of("1ab14599", "incorrect birth year"),
                Arguments.of("19814x08", "incorrect serial number"),
                Arguments.of("19800074", "incorrect serial number"),
                Arguments.of("19912378", "incorrect control key")
        );
    }

    public static Stream<Arguments> validEIDs() {
        return Stream.of(
                Arguments.of("19845606"),
                Arguments.of("30600233"),
                Arguments.of("29999922"),
                Arguments.of("11111151"),
                Arguments.of("19800767")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidEIDs")
    void return_false_for_invalid_EIDs(String invalidEID, String reason) {
        assertThat(EID.validate(invalidEID))
                .as(reason)
                .isFalse();
    }

    @ParameterizedTest
    @MethodSource("validEIDs")
    void return_true_for_valid_EIDs(String validEID) {
        assertThat(EID.validate(validEID))
                .isTrue();
    }
}