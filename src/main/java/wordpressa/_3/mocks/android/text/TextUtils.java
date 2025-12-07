package wordpressa._3.mocks.android.text;

public class TextUtils {
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }
    
    public static CharSequence concat(CharSequence... text) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence t : text) {
            if (t != null) sb.append(t);
        }
        return sb.toString();
    }
    
    public static String join(CharSequence delimiter, Object[] tokens) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Object token : tokens) {
            if (first) {
                first = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }
    
    public static int indexOf(CharSequence s, char ch) {
        return indexOf(s, ch, 0);
    }
    
    public static int indexOf(CharSequence s, char ch, int start) {
        if (s instanceof String) {
            return ((String) s).indexOf(ch, start);
        }
        int len = s.length();
        for (int i = start; i < len; i++) {
            if (s.charAt(i) == ch) return i;
        }
        return -1;
    }
    
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        if (a.length() != b.length()) return false;
        if (a instanceof String && b instanceof String) {
            return a.equals(b);
        }
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) return false;
        }
        return true;
    }
    
    public interface TruncateAt {
        // Placeholder
    }
}
