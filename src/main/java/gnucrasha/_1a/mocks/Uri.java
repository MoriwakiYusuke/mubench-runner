package gnucrasha._1a.mocks;

import java.io.File;

/**
 * Minimal URI wrapper storing a file path.
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
