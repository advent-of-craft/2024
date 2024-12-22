package eid;

import io.vavr.control.Option;
import lombok.experimental.UtilityClass;

import static io.vavr.control.Option.none;
import static io.vavr.control.Option.some;
import static java.lang.Integer.parseInt;

@UtilityClass
public class StringExtensions {
    public static Option<Integer> toInt(String potentialNumber) {
        return isANumber(potentialNumber) // Use Option<Integer> -> equivalent to Optional since java 8
                ? some(parseInt(potentialNumber))
                : none();
    }

    private static boolean isANumber(String str) {
        return str != null && str.matches("[0-9.]+");
    }
}