package communication;

public class SantaCommunicator {
    private final int numberOfDaysToRest;

    public SantaCommunicator(int numberOfDaysToRest) {
        this.numberOfDaysToRest = numberOfDaysToRest;
    }

    public String composeMessage(String reindeerName, String currentLocation, int numbersOfDaysForComingBack, int numberOfDaysBeforeChristmas) {
        var daysBeforeReturn = daysBeforeReturn(numbersOfDaysForComingBack, numberOfDaysBeforeChristmas);

        return "Dear " + reindeerName + ", please return from " + currentLocation +
                " in " + daysBeforeReturn + " day(s) to be ready and rest before Christmas.";
    }

    public boolean isOverdue(String reindeerName, String currentLocation, int numbersOfDaysForComingBack, int numberOfDaysBeforeChristmas, Logger logger) {
        if (daysBeforeReturn(numbersOfDaysForComingBack, numberOfDaysBeforeChristmas) <= 0) {
            logger.log("Overdue for " + reindeerName + " located " + currentLocation + ".");
            return true;
        }
        return false;
    }

    private int daysBeforeReturn(int numbersOfDaysForComingBack, int numberOfDaysBeforeChristmas) {
        return numberOfDaysBeforeChristmas - numbersOfDaysForComingBack - numberOfDaysToRest;
    }
}