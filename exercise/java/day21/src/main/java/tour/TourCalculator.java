package tour;

import io.vavr.collection.List;
import io.vavr.control.Either;

import java.time.Duration;
import java.util.Comparator;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class TourCalculator {
    private List<Step> steps;
    private boolean calculated = false;
    private double deliveryTime = 0;

    public TourCalculator(List<Step> steps) {
        this.steps = steps;
    }

    public boolean isCalculated() {
        return calculated;
    }

    public double getDeliveryTime() {
        return deliveryTime;
    }

    public Either<String, String> calculate() {
        if (steps == null || steps.isEmpty()) {
            return left("No locations !!!");
        } else {
            StringBuilder result = new StringBuilder();

            for (Step s : steps.sorted(Comparator.comparing(Step::time))) {
                if (!calculated) {
                    this.deliveryTime += s.deliveryTime();
                    result.append(fLine(s, deliveryTime)).append("\n");
                }
            }

            String str = formatDurationToHHMMSS(Duration.ofSeconds((long) this.deliveryTime));
            result.append("Delivery time | ").append(str);
            calculated = true;

            return right(result.toString());
        }
    }

    private static String formatDurationToHHMMSS(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private String fLine(Step step, double x) {
        if (step == null)
            throw new IllegalStateException();
        else
            return step.time() + " : " + step.label() + " | " + step.deliveryTime() + " sec";
    }
}