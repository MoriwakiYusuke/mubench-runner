package wordpressa._3.mocks.android.net;

import java.io.File;

public class Uri {
    private String uriString;
    
    public Uri() {}
    public Uri(String uriString) { this.uriString = uriString; }
    
    public static Uri parse(String uriString) {
        return new Uri(uriString);
    }
    
    public static Uri fromFile(File file) {
        return new Uri(file != null ? "file://" + file.getAbsolutePath() : "");
    }
    
    public String toString() { return uriString; }
    public String getPath() { return uriString; }
    public String getEncodedPath() { return uriString; }
    public String getScheme() { return "file"; }
    public String getLastPathSegment() { return ""; }
    
    public static Builder builder() { return new Builder(); }
    
    public static class Builder {
        private StringBuilder sb = new StringBuilder();
        public Builder scheme(String scheme) { return this; }
        public Builder authority(String authority) { return this; }
        public Builder path(String path) { sb.append(path); return this; }
        public Builder appendPath(String newSegment) { sb.append("/").append(newSegment); return this; }
        public Uri build() { return new Uri(sb.toString()); }
    }
}
