package wordpressa._3.mocks.org.wordpress.android.util;

public class StringUtils {
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    public static String notNullStr(String str) {
        return str == null ? "" : str;
    }
    
    public static String escapeHtml(String html) {
        return html;
    }
    
    public static String unescapeHtml(String html) {
        return html;
    }
    
    public static String addPTags(String text) {
        return text;
    }
    
    public static String getPhotonUrl(String url, int width) {
        return url;
    }
}
