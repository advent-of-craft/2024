package core.control;

import core.control.sleigh.Action;

public class SystemErrors {
    public static final String SLEIGH_NOT_STARTED = "The sleigh is not started. Please start the sleigh before any other action...";
    public static final String SLEIGH_STILL_RUNNING = "The sleigh is still on";
    public static final String SLEIGH_DID_NOT_START = "The sleigh did not start correctly";
    public static final String REINDEER_NEEDS_REST = "The reindeer needs rest. Please park the sleigh...";
    public static final String NOT_ENOUGH_MAGICAL_POWER = "System is not usable. It needs more magical power.";
    public static final String SLEIGH_ALREADY_STARTED = "System already started";

    private SystemErrors() {
    }

    public static String failingTo(Action action) {
        return "Action failed. Sleigh is not " + action.toString() + ".";
    }
}
