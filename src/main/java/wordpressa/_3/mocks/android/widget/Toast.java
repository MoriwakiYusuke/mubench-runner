package wordpressa._3.mocks.android.widget;

import wordpressa._3.mocks.android.content.Context;

public class Toast {
    public static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;
    
    private CharSequence text;
    private int duration;
    
    public static Toast makeText(Context context, CharSequence text, int duration) {
        Toast toast = new Toast();
        toast.text = text;
        toast.duration = duration;
        return toast;
    }
    
    public static Toast makeText(Context context, int resId, int duration) {
        return makeText(context, "", duration);
    }
    
    public void show() {}
    public void cancel() {}
    public void setDuration(int duration) { this.duration = duration; }
    public void setText(CharSequence s) { this.text = s; }
    public void setGravity(int gravity, int xOffset, int yOffset) {}
}
