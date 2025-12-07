package wordpressa._3.mocks.com.android.volley.toolbox;

import wordpressa._3.mocks.android.graphics.Bitmap;
import wordpressa._3.mocks.com.android.volley.RequestQueue;
import wordpressa._3.mocks.com.android.volley.VolleyError;

public class ImageLoader {
    public interface ImageListener {
        void onResponse(ImageContainer response, boolean isImmediate);
        void onErrorResponse(VolleyError error);
    }
    
    public interface ImageCache {
        Bitmap getBitmap(String url);
        void putBitmap(String url, Bitmap bitmap);
    }
    
    public static class ImageContainer {
        public Bitmap getBitmap() { return null; }
        public String getRequestUrl() { return ""; }
        public void cancelRequest() {}
    }
    
    public ImageLoader(RequestQueue queue, ImageCache imageCache) {}
    
    public ImageContainer get(String requestUrl, ImageListener listener) {
        return new ImageContainer();
    }
    
    public ImageContainer get(String requestUrl, ImageListener listener, int maxWidth, int maxHeight) {
        return new ImageContainer();
    }
}
