package wordpressa._3.mocks.android.widget;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.android.view.View;

public class ImageView extends View {
    private int imageResource;
    
    public ImageView() { super(); }
    public ImageView(Context context) { super(context); }
    
    public void setImageResource(int resId) { this.imageResource = resId; }
    public void setImageDrawable(android.graphics.drawable.Drawable drawable) {}
    public void setImageBitmap(android.graphics.Bitmap bm) {}
    public void setScaleType(ScaleType scaleType) {}
    
    public enum ScaleType {
        MATRIX, FIT_XY, FIT_START, FIT_CENTER, FIT_END, CENTER, CENTER_CROP, CENTER_INSIDE
    }
}
