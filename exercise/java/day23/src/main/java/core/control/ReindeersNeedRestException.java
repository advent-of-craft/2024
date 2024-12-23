package core.control;

public class ReindeersNeedRestException extends Throwable {
    @Override
    public String getMessage() {
        return "The reindeer needs rest. Please park the sleigh...";
    }
}
