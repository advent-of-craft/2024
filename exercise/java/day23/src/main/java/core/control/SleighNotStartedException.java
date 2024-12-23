package core.control;

public class SleighNotStartedException extends Throwable {
    @Override
    public String getMessage() {
        return "The sleigh is not started. Please start the sleigh before any other action...";
    }
}
