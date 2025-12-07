package wordpressa._3.mocks.android.widget;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.android.view.ViewGroup;

public class RelativeLayout extends ViewGroup {
    public static final int ALIGN_PARENT_LEFT = 9;
    public static final int ALIGN_PARENT_RIGHT = 11;
    public static final int ALIGN_PARENT_TOP = 10;
    public static final int ALIGN_PARENT_BOTTOM = 12;
    public static final int CENTER_IN_PARENT = 13;
    public static final int CENTER_HORIZONTAL = 14;
    public static final int CENTER_VERTICAL = 15;
    
    public RelativeLayout() { super(); }
    public RelativeLayout(Context context) { super(context); }
    
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public LayoutParams() { super(); }
        public LayoutParams(int width, int height) { super(width, height); }
        
        public void addRule(int verb) {}
        public void addRule(int verb, int anchor) {}
        public void removeRule(int verb) {}
    }
}
