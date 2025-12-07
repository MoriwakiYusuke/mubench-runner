package wordpressa._3.mocks.android.text.style;

import wordpressa._3.mocks.android.graphics.Bitmap;

public class ImageSpan extends CharacterStyle {
    public static final int ALIGN_BOTTOM = 0;
    public static final int ALIGN_BASELINE = 1;
    
    private String source;
    private Bitmap bitmap;
    
    public ImageSpan(Bitmap b) { this.bitmap = b; }
    public ImageSpan(Bitmap b, int verticalAlignment) { this.bitmap = b; }
    public ImageSpan(android.graphics.drawable.Drawable d) {}
    public ImageSpan(android.graphics.drawable.Drawable d, int verticalAlignment) {}
    public ImageSpan(android.graphics.drawable.Drawable d, String source) { this.source = source; }
    public ImageSpan(android.graphics.drawable.Drawable d, String source, int verticalAlignment) { this.source = source; }
    
    public String getSource() { return source; }
    public android.graphics.drawable.Drawable getDrawable() { return null; }
}
