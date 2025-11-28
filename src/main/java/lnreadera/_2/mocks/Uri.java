package lnreadera._2.mocks;

public class Uri {
    private String uriString;
    
    private Uri(String uriString) {
        this.uriString = uriString;
    }
    
    public static Uri parse(String uriString) {
        return new Uri(uriString);
    }
    
    public static Uri fromFile(java.io.File file) {
        return new Uri("file://" + file.getAbsolutePath());
    }
    
    @Override
    public String toString() {
        return uriString;
    }
    
    public String getPath() {
        return uriString;
    }
}
