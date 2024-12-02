package games;

import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;

import static io.vavr.control.Option.none;
import static io.vavr.control.Option.some;

public class FizzBuzz {
    public static final int MIN = 1;
    public static final int MAX = 100;
    private static final Map<Integer, String> mapping = LinkedHashMap.of(
            15, "FizzBuzz",
            3, "Fizz",
            5, "Buzz"
    );

    public static Option<String> convert(int input) {
        return isOutOfRange(input)
                ? none()
                : some(convertSafely(input));
    }

    private static String convertSafely(Integer input) {
        return mapping
                .find(p -> is(p._1, input))
                .map(p -> p._2)
                .getOrElse(input.toString());
    }

    private static boolean is(Integer divisor, Integer input) {
        return input % divisor == 0;
    }

    private static boolean isOutOfRange(Integer input) {
        return input < MIN || input > MAX;
    }
}