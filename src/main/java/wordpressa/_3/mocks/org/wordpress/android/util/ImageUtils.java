package wordpressa._3.mocks.org.wordpress.android.util;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.android.graphics.Bitmap;

public class ImageUtils {
    public static Bitmap getScaledBitmap(String filePath, int maxWidth, int maxHeight) {
        return new Bitmap();
    }
    
    public static String getMediaFilePath(Context context, Object uri) {
        return "";
    }
    
    public static int[] getImageSize(String filePath) {
        return new int[]{100, 100};
    }
}
