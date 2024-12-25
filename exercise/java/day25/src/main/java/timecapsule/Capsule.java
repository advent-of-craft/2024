package timecapsule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.nio.file.Files.*;

public class Capsule {
    private static final String TEMPLATE = "timecapsule_template.html";
    public static final String FILE_PATH = "timecapsule.html";

    private String pastMessage;
    private LocalDateTime timestamp;
    private final boolean hasPastMessage;

    public Capsule() {
        hasPastMessage = loadPastMessage();
    }

    public String getPastMessage() {
        return pastMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean hasPastMessage() {
        return hasPastMessage;
    }

    private boolean loadPastMessage() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return false;
        }

        try {
            var path = Paths.get(FILE_PATH);
            var htmlContent = new String(readAllBytes(path));
            var startIndex = htmlContent.indexOf("<!--MESSAGE_START-->");
            var endIndex = htmlContent.indexOf("<!--MESSAGE_END-->");

            if (startIndex == -1 || endIndex == -1) {
                return false;
            }

            pastMessage = htmlContent.substring(startIndex + 18, endIndex).trim();
            timestamp = LocalDateTime.parse(
                    getLastModifiedTime(path).toString(),
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME
            );

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void saveMessage(String message) throws IOException {
        var templateFile = new File(TEMPLATE);
        if (!templateFile.exists()) {
            throw new IOException("HTML template file not found!");
        }

        var filledContent = new String(readAllBytes(Paths.get(TEMPLATE)))
                .replace("{{message}}", message)
                .replace("{{timestamp}}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm:ss")));

        write(Paths.get(FILE_PATH), filledContent.getBytes());

        this.timestamp = LocalDateTime.now();
        this.pastMessage = message;
    }
}
