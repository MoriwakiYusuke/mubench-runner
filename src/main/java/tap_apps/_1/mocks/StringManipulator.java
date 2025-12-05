package tap_apps._1.mocks;

/**
 * Mock for org.jSyncManager.API.Tools.StringManipulator
 */
public class StringManipulator {
    
    public static String generateValidFilename(String input) {
        if (input == null) return "";
        // Replace invalid characters with underscores
        return input.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
