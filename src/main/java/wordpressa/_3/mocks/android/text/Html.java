package wordpressa._3.mocks.android.text;

public class Html {
    public static final int FROM_HTML_MODE_LEGACY = 0;
    public static final int FROM_HTML_MODE_COMPACT = 63;
    
    public static Spanned fromHtml(String source) {
        return new SpannableString(source);
    }
    
    public static Spanned fromHtml(String source, int flags) {
        return new SpannableString(source);
    }
    
    public static Spanned fromHtml(String source, ImageGetter imageGetter, TagHandler tagHandler) {
        return new SpannableString(source);
    }
    
    public static String toHtml(Spanned text) {
        return text != null ? text.toString() : "";
    }
    
    public static String toHtml(Spanned text, int option) {
        return text != null ? text.toString() : "";
    }
    
    public interface ImageGetter {
        android.graphics.drawable.Drawable getDrawable(String source);
    }
    
    public interface TagHandler {
        void handleTag(boolean opening, String tag, Editable output, org.xml.sax.XMLReader xmlReader);
    }
}
