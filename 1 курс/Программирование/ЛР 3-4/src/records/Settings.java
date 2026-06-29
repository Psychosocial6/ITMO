package records;

import java.util.HashMap;

public record Settings(HashMap<String, HashMap<String, Object>> config) {

    public Settings(HashMap<String, HashMap<String, Object>> config) {
        this.config = config;
    }

    public void sleep(int sleepingTime) {
        try {
            Thread.sleep(sleepingTime * 1000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
