package cego._1.mocks;

/**
 * Simplified drawable wrapper around a bitmap.
 */
public class BitmapDrawable {

    private final Bitmap bitmap;

    public BitmapDrawable(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
