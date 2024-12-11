package christmas;

public class Preparation {
    private Preparation() {
    }

    public static String prepareGifts(int numberOfGifts) {
        if (numberOfGifts <= 0) {
            return "No gifts to prepare.";
        } else if (numberOfGifts < 50) {
            return "Elves will prepare the gifts.";
        }
        return "Santa will prepare the gifts.";
    }

    public static String categorizeGift(int age) {
        if (age <= 2) {
            return "Baby";
        } else if (age <= 5) {
            return "Toddler";
        } else if (age <= 12) {
            return "Child";
        }
        return "Teen";
    }

    public static boolean ensureToyBalance(ToyType toyType, int toysCount, int totalToys) {
        double typePercentage = (double) toysCount / totalToys;

        return switch (toyType) {
            case EDUCATIONAL -> typePercentage >= 0.25;
            case FUN -> typePercentage >= 0.30;
            case CREATIVE -> typePercentage >= 0.20;
        };
    }
}