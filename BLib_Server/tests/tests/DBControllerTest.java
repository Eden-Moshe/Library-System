package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import server.DBController;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.List;

public class DBControllerTest {
    private final DBController controller = DBController.getInstance();

    @Test
    public void testLogErrorPrepend() throws Exception {
        String firstError = "First Error";
        Throwable firstThrowable = new RuntimeException("First Exception");

        String secondError = "Second Error";
        Throwable secondThrowable = new RuntimeException("Second Exception");

        // Clear the log file
        File errorLog = new File(System.getProperty("user.home") + File.separator + "DBController_Error_Log.txt");
        if (errorLog.exists()) {
            errorLog.delete();
        }

        // Use reflection to access the private method
        Method logErrorMethod = DBController.class.getDeclaredMethod("logError", String.class, Throwable.class);
        logErrorMethod.setAccessible(true);

        // Log the first error
        logErrorMethod.invoke(controller, firstError, firstThrowable);

        // Log the second error
        logErrorMethod.invoke(controller, secondError, secondThrowable);

        // Verify the order of the errors in the file
        List<String> lines = Files.readAllLines(errorLog.toPath());
        String fileContent = String.join("\n", lines);

        assertTrue(fileContent.startsWith(secondError));
        assertTrue(fileContent.contains(firstError));
        assertTrue(fileContent.indexOf(secondError) < fileContent.indexOf(firstError));
    }
}

