package cego._1.mocks;

import java.io.File;

/**
 * Simplified URI wrapper storing file paths.
 */
public class Uri {

    private final String path;

    private Uri(String path) {
        this.path = path;
    }

    public static Uri fromFile(File file) {
        return new Uri(file.getAbsolutePath());
    }

    public String getPath() {
        return path;
    }
}
