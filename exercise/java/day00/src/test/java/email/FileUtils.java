package email;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
    public static String loadFile(String fileName) throws Exception {
        var classLoader = EncryptionTest.class.getClassLoader();
        var resource = classLoader.getResource(fileName);

        if (resource == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }
        return Files.readString(Paths.get(resource.toURI()));
    }

}
