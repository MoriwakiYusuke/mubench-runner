package wordpressa._3.mocks.org.wordpress.android;

import wordpressa._3.mocks.android.app.Activity;
import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.com.android.volley.toolbox.ImageLoader;
import wordpressa._3.mocks.org.wordpress.android.models.Blog;

public class WordPress {
    public static ImageLoader imageLoader;
    public static Blog currentBlog;
    public static Activity currentActivity;
    public static WordPressDB wpDB = new WordPressDB();
    
    public static Context getContext() {
        return new Context();
    }
    
    public static void toast(String message) {}
    public static void toast(int resId) {}
    
    public static boolean isSignedIn(Context context) {
        return true;
    }
    
    public static Blog getCurrentBlog() {
        return currentBlog;
    }
    
    public static class WordPressDB {
        public boolean isPhotonCapable() { return true; }
        public String getPhotonUrl(String url, int width) { return url; }
        public void updateMediaFile(String blogId, String mediaId, String title, String description, String caption) {}
    }
}
