package eid;

import static java.lang.Integer.parseInt;

public class EID {
    public static final String VALID_EID_REGEX = "^[123]\\d{7}$";

    public static boolean validate(String potentialEID) {
        return validateFormat(potentialEID)
                && validateSerialNumber(potentialEID)
                && validateKey(potentialEID);
    }

    private static boolean validateFormat(String potentialEID) {
        return potentialEID != null && potentialEID.matches(VALID_EID_REGEX);
    }

    private static boolean validateKey(String potentialEID) {
        return extractKey(potentialEID) == calculateKeyFor(toInt(potentialEID));
    }

    private static boolean validateSerialNumber(String potentialEID) {
        return parseInt(potentialEID.substring(3, 6)) > 0;
    }

    private static int extractKey(String potentialEID) {
        return parseInt(potentialEID.substring(6, 8));
    }

    private static int calculateKeyFor(int number) {
        return 97 - (number % 97);
    }

    private static int toInt(String potentialEID) {
        return parseInt(potentialEID.substring(0, 6));
    }
}