package doubles;

import communication.Logger;

public class TestLogger implements Logger {
    private String message;

    @Override
    public void log(String message) {
        this.message = message;
    }

    public String getLog() {
        return message;
    }
}