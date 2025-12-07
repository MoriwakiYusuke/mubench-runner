package wordpressa._3.mocks.android.graphics;

import java.io.InputStream;

public class BitmapFactory {
    
    public static Bitmap decodeFile(String pathName) {
        return new Bitmap();
    }
    
    public static Bitmap decodeFile(String pathName, Options opts) {
        return new Bitmap();
    }
    
    public static Bitmap decodeStream(InputStream is) {
        return new Bitmap();
    }
    
    public static Bitmap decodeStream(InputStream is, Object outPadding, Options opts) {
        return new Bitmap();
    }
    
    public static Bitmap decodeResource(Object res, int id) {
        return new Bitmap();
    }
    
    public static Bitmap decodeByteArray(byte[] data, int offset, int length) {
        return new Bitmap();
    }
    
    public static class Options {
        public boolean inJustDecodeBounds = false;
        public int inSampleSize = 1;
        public int outWidth;
        public int outHeight;
        public Bitmap.Config inPreferredConfig;
        public boolean inMutable;
    }
}
