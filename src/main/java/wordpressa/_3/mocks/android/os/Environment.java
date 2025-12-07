package wordpressa._3.mocks.android.os;

import java.io.File;

public class Environment {
    public static final String DIRECTORY_PICTURES = "Pictures";
    public static final String DIRECTORY_DCIM = "DCIM";
    public static final String DIRECTORY_DOWNLOADS = "Download";
    public static final String DIRECTORY_MOVIES = "Movies";
    public static final String DIRECTORY_MUSIC = "Music";
    public static final String DIRECTORY_DOCUMENTS = "Documents";
    
    public static final String MEDIA_MOUNTED = "mounted";
    public static final String MEDIA_REMOVED = "removed";
    public static final String MEDIA_UNMOUNTED = "unmounted";
    
    public static File getExternalStorageDirectory() {
        return new File("/sdcard");
    }
    
    public static File getExternalStoragePublicDirectory(String type) {
        return new File("/sdcard/" + type);
    }
    
    public static String getExternalStorageState() {
        return MEDIA_MOUNTED;
    }
    
    public static File getDataDirectory() {
        return new File("/data");
    }
    
    public static File getRootDirectory() {
        return new File("/system");
    }
    
    public static boolean isExternalStorageRemovable() {
        return true;
    }
    
    public static boolean isExternalStorageEmulated() {
        return true;
    }
}
