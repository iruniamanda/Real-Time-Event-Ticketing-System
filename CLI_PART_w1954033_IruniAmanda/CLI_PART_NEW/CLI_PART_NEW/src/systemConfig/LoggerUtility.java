package systemConfig;

import java.time.LocalDateTime;

public class LoggerUtility {
    public static synchronized void log(String message) {
        System.out.println("[" + LocalDateTime.now() + "] " + message);
    }
}


