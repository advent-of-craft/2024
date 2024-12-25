package timecapsule;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class Browser {
    public static void open(String filePath) {
        try {
            var file = new File(filePath);
            if (file.exists() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(file.toURI());
            } else {
                throw new IOException("File not found or desktop environment not supported.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}