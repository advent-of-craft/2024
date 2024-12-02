package communication;

public class SantaCommunicator {
    private final Configuration configuration;

    public SantaCommunicator(Configuration configuration) {
        this.configuration = configuration;
    }

    public String composeMessage(Reindeer reindeer) {
        return "Dear " + reindeer.name() + ", please return from " + reindeer.location() +
                " in " + daysBeforeReturn(reindeer) + " day(s) to be ready and rest before Christmas.";
    }

    public boolean isOverdue(Reindeer reindeer, Logger logger) {
        if (daysBeforeReturn(reindeer) <= 0) {
            logger.log("Overdue for " + reindeer.name() + " located " + reindeer.location() + ".");
            return true;
        }
        return false;
    }

    private int daysBeforeReturn(Reindeer reindeer) {
        return configuration.numberOfDayBeforeChristmas() - reindeer.numbersOfDaysForComingBack() - configuration.numberOfDaysToRest();
    }
}