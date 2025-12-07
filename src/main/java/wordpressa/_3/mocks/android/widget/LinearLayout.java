package wordpressa._3.mocks.android.widget;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.android.view.ViewGroup;

public class LinearLayout extends ViewGroup {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    
    private int orientation = HORIZONTAL;
    
    public LinearLayout() { super(); }
    public LinearLayout(Context context) { super(context); }
    
    public void setOrientation(int orientation) { this.orientation = orientation; }
    public int getOrientation() { return orientation; }
    
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public float weight;
        public int gravity;
        
        public LayoutParams() { super(); }
        public LayoutParams(int width, int height) { super(width, height); }
        public LayoutParams(int width, int height, float weight) {
            super(width, height);
            this.weight = weight;
        }
    }
}
