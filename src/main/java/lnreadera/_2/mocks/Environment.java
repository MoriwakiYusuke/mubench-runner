package lnreadera._2.mocks;

public class Environment {
    public static final String MEDIA_MOUNTED = "mounted";
    
    public static String getExternalStorageState() {
        return MEDIA_MOUNTED;
    }
    
    public static java.io.File getExternalStorageDirectory() {
        return new java.io.File("/storage/emulated/0");
    }
}
