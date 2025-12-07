package wordpressa._3.mocks.android.view;

import wordpressa._3.mocks.android.content.Context;

public class ViewGroup extends View {
    public ViewGroup() {}
    public ViewGroup(Context context) { super(context); }
    
    public void addView(View child) {}
    public void addView(View child, int index) {}
    public void addView(View child, LayoutParams params) {}
    public void addView(View child, int index, LayoutParams params) {}
    public void removeView(View view) {}
    public void removeViewAt(int index) {}
    public void removeAllViews() {}
    public int getChildCount() { return 0; }
    public View getChildAt(int index) { return null; }
    public int indexOfChild(View child) { return -1; }
    
    public static class LayoutParams {
        public static final int MATCH_PARENT = -1;
        public static final int WRAP_CONTENT = -2;
        public static final int FILL_PARENT = -1;
        
        public int width;
        public int height;
        
        public LayoutParams() {}
        public LayoutParams(int width, int height) {
            this.width = width;
            this.height = height;
        }
        public LayoutParams(LayoutParams source) {
            this.width = source.width;
            this.height = source.height;
        }
    }
    
    public static class MarginLayoutParams extends LayoutParams {
        public int leftMargin;
        public int topMargin;
        public int rightMargin;
        public int bottomMargin;
        
        public MarginLayoutParams() {}
        public MarginLayoutParams(int width, int height) { super(width, height); }
        public MarginLayoutParams(LayoutParams source) { super(source); }
        public MarginLayoutParams(MarginLayoutParams source) {
            super(source);
            this.leftMargin = source.leftMargin;
            this.topMargin = source.topMargin;
            this.rightMargin = source.rightMargin;
            this.bottomMargin = source.bottomMargin;
        }
        
        public void setMargins(int left, int top, int right, int bottom) {
            leftMargin = left;
            topMargin = top;
            rightMargin = right;
            bottomMargin = bottom;
        }
    }
}
