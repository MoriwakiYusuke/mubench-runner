package cego._1.mocks;

import java.io.File;

/**
 * Simplified storage helper returning files under the system temp directory.
 */
public final class LocalStorage {

    private LocalStorage() {
    }

    public static File getStorageFile(String directory, String name, boolean publiclyAccessible, boolean createDirs) {
        File base = new File(System.getProperty("java.io.tmpdir"), "cego");
        if (directory != null && !directory.isEmpty()) {
            base = new File(base, directory);
        }
        if (createDirs && !base.exists()) {
            base.mkdirs();
        }
        return new File(base, name);
    }
}
