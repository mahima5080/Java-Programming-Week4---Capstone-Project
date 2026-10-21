import java.io.File;
import java.io.IOException;

public class DatabaseConfig {
    public static final String FILE_NAME = "students.txt";

    public static void initializeStorage() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("❌ Storage Error: " + e.getMessage());
            }
        }
    }
}