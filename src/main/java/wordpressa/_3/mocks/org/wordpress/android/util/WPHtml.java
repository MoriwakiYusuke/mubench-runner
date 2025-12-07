package wordpressa._3.mocks.org.wordpress.android.util;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.org.wordpress.android.models.Post;
import wordpressa._3.mocks.org.wordpress.android.ui.posts.EditPostActivity;
import wordpressa._3.mocks.org.wordpress.android.util.WPImageSpan;
import wordpressa._3.mocks.org.wordpress.android.util.MediaGalleryImageSpan;

public class WPHtml {
    public static CharSequence fromHtml(String source) {
        return source;
    }
    
    public static CharSequence fromHtml(String source, Object context, Object post) {
        return source;
    }
    
    public static CharSequence fromHtml(String source, Context context, Post post) {
        return source;
    }
    
    public static CharSequence fromHtml(String source, EditPostActivity activity, Post post) {
        return source;
    }
    
    public static CharSequence fromHtml(String source, Context context, Object post, int maxWidth) {
        return source;
    }
    
    public static String toHtml(CharSequence text) {
        return text != null ? text.toString() : "";
    }
    
    public static String getContent(WPImageSpan span) {
        return "";
    }
    
    public static String getGalleryShortcode(MediaGalleryImageSpan span) {
        return "";
    }
}
