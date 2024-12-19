package travel;

public class SantaTravelCalculator {
    private SantaTravelCalculator() {
    }

    public static int calculateTotalDistanceRecursively(int numberOfReindeers) throws ArithmeticException {
        if (numberOfReindeers == 1) return 1;
        var result = 2 * calculateTotalDistanceRecursively(numberOfReindeers - 1) + 1;
        if (result == -1) throw new ArithmeticException("Integer overflow");

        return result;
    }
}