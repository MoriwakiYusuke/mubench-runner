package wordpressa._3.mocks.android.graphics;

public class Bitmap {
    public int getWidth() { return 100; }
    public int getHeight() { return 100; }
    public void recycle() {}
    public boolean isRecycled() { return false; }
    
    public static Bitmap createBitmap(int width, int height, Config config) {
        return new Bitmap();
    }
    
    public static Bitmap createScaledBitmap(Bitmap src, int width, int height, boolean filter) {
        return new Bitmap();
    }
    
    public enum Config {
        ARGB_8888, RGB_565, ALPHA_8, ARGB_4444
    }
}
