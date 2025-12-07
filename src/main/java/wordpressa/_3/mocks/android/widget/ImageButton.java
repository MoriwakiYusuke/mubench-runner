package wordpressa._3.mocks.android.widget;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.android.view.View;

public class ImageButton extends View {
    private int imageResource;
    
    public ImageButton() { super(); }
    public ImageButton(Context context) { super(context); }
    
    public void setImageResource(int resId) { this.imageResource = resId; }
    public void setImageDrawable(android.graphics.drawable.Drawable drawable) {}
    public void setImageBitmap(android.graphics.Bitmap bm) {}
}
