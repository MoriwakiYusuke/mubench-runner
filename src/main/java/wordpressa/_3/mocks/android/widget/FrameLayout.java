package wordpressa._3.mocks.android.widget;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.android.view.ViewGroup;

public class FrameLayout extends ViewGroup {
    public FrameLayout() { super(); }
    public FrameLayout(Context context) { super(context); }
    
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int gravity;
        
        public LayoutParams() { super(); }
        public LayoutParams(int width, int height) { super(width, height); }
        public LayoutParams(int width, int height, int gravity) {
            super(width, height);
            this.gravity = gravity;
        }
    }
}
