package communication;

public class SantaCommunicator {
    private final ReindeerPlanner reindeerPlanner;

    public SantaCommunicator(int numberOfDaysToRest) {
        this.reindeerPlanner = new ReindeerPlanner(numberOfDaysToRest);
    }

    public String composeMessage(String reindeerName, ReindeerLocation reindeerLocation, int numberOfDaysBeforeChristmas) {
        var daysBeforeReturn = reindeerPlanner.daysBeforeReturn(reindeerLocation, numberOfDaysBeforeChristmas);

        return "Dear " + reindeerName + ", please return from " + reindeerLocation.currentLocation() +
               " in " + daysBeforeReturn + " day(s) to be ready and rest before Christmas.";
    }

    public boolean isOverdue(String reindeerName, ReindeerLocation reindeerLocation, int numberOfDaysBeforeChristmas, Logger logger) {
        if (!reindeerLocation.isReachableInDays(reindeerPlanner.numberOfDaysBeforeRestingBeforeChristmas(numberOfDaysBeforeChristmas))) {
            logger.log("Overdue for " + reindeerName + " located " + reindeerLocation.currentLocation() + ".");
            return true;
        }
        return false;
    }

    public static class ReindeerPlanner {
        private final int numberOfDaysToRest;

        public ReindeerPlanner(int numberOfDaysToRest) {
            this.numberOfDaysToRest = numberOfDaysToRest;
        }

        private int numberOfDaysBeforeRestingBeforeChristmas(int numberOfDaysBeforeChristmas) {
            return numberOfDaysBeforeChristmas - numberOfDaysToRest;
        }

        private int daysBeforeReturn(ReindeerLocation reindeerLocation, int numberOfDaysBeforeChristmas) {
            return numberOfDaysBeforeRestingBeforeChristmas(numberOfDaysBeforeChristmas)
                   - reindeerLocation.distanceFromSantaHouseInDays();
        }
    }

    public record ReindeerLocation(String currentLocation, int distanceFromSantaHouseInDays) {
        boolean isReachableInDays(int daysToTravel) {
            return daysToTravel > distanceFromSantaHouseInDays();
        }
    }
}