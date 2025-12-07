package wordpressa._3.mocks.org.wordpress.android.util;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.android.graphics.Bitmap;
import wordpressa._3.mocks.android.net.Uri;
import java.util.Map;
import java.util.HashMap;

public class ImageHelper {
    public static Bitmap getScaledBitmap(String filePath, int maxWidth, int maxHeight) {
        return new Bitmap();
    }
    
    public static int[] getImageSize(String filePath) {
        return new int[]{100, 100};
    }
    
    public static String createThumbnailFromUri(Context context, Object uri, int width, int height, String filePath) {
        return filePath;
    }
    
    public static boolean imageFileExists(String filePath) {
        return true;
    }
    
    public Bitmap getThumbnailForWPImageSpan(Bitmap bitmap, int size) {
        return bitmap;
    }
    
    public Bitmap getThumbnailForWPImageSpan(Object activity, byte[] bytes, String orientation) {
        return new Bitmap();
    }
    
    public void setWPImageSpanWidth(Object activity, Uri uri, WPImageSpan span) {}
    
    public static String getPhotonUrl(String imageUrl, int maxWidth) { return imageUrl; }
    
    public static int getMinimumImageWidth(Object activity, String imagePath) { return 100; }
    
    public Map<String, Object> getImageBytesForPath(String path, Object activity) {
        Map<String, Object> result = new HashMap<>();
        result.put("bytes", new byte[0]);
        result.put("orientation", "0");
        return result;
    }
}
