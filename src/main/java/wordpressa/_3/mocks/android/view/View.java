package wordpressa._3.mocks.android.view;

import wordpressa._3.mocks.android.content.Context;

public class View {
    public static final int VISIBLE = 0x00000000;
    public static final int INVISIBLE = 0x00000004;
    public static final int GONE = 0x00000008;
    
    public static final int FOCUS_DOWN = 0x00000082;
    public static final int FOCUS_UP = 0x00000021;
    public static final int FOCUS_LEFT = 0x00000011;
    public static final int FOCUS_RIGHT = 0x00000042;
    
    protected Context context;
    protected int visibility = VISIBLE;
    protected int id;
    protected OnClickListener clickListener;
    protected OnLongClickListener longClickListener;
    protected OnTouchListener touchListener;
    protected OnFocusChangeListener focusChangeListener;
    
    public View() {}
    public View(Context context) { this.context = context; }
    
    public Context getContext() { return context; }
    public void setVisibility(int visibility) { this.visibility = visibility; }
    public int getVisibility() { return visibility; }
    
    public void setId(int id) { this.id = id; }
    public int getId() { return id; }
    
    public View findViewById(int id) { return null; }
    
    public void setOnClickListener(OnClickListener l) { this.clickListener = l; }
    public void setOnLongClickListener(OnLongClickListener l) { this.longClickListener = l; }
    public void setOnTouchListener(OnTouchListener l) { this.touchListener = l; }
    public void setOnFocusChangeListener(OnFocusChangeListener l) { this.focusChangeListener = l; }
    
    public void performClick() {
        if (clickListener != null) clickListener.onClick(this);
    }
    
    public boolean performLongClick() {
        if (longClickListener != null) return longClickListener.onLongClick(this);
        return false;
    }
    
    public void requestFocus() {}
    public boolean hasFocus() { return false; }
    public void clearFocus() {}
    
    public void setEnabled(boolean enabled) {}
    public boolean isEnabled() { return true; }
    
    public void setTag(Object tag) {}
    public Object getTag() { return null; }
    public void setTag(int key, Object tag) {}
    public Object getTag(int key) { return null; }
    
    public int getWidth() { return 100; }
    public int getHeight() { return 100; }
    public int getTop() { return 0; }
    public int getBottom() { return 100; }
    public int getLeft() { return 0; }
    public int getRight() { return 100; }
    
    public void invalidate() {}
    public void postInvalidate() {}
    public void requestLayout() {}
    
    public boolean post(Runnable action) { if (action != null) action.run(); return true; }
    public boolean postDelayed(Runnable action, long delayMillis) { if (action != null) action.run(); return true; }
    
    public void setBackgroundColor(int color) {}
    public void setBackgroundResource(int resid) {}
    public void setPadding(int left, int top, int right, int bottom) {}
    
    public ViewGroup.LayoutParams getLayoutParams() { return null; }
    public void setLayoutParams(ViewGroup.LayoutParams params) {}
    
    public void scrollTo(int x, int y) {}
    public void scrollBy(int x, int y) {}
    public int getScrollX() { return 0; }
    public int getScrollY() { return 0; }
    
    public void setContentDescription(CharSequence contentDescription) {}
    
    public Object getWindowToken() { return new Object(); }
    
    public ViewTreeObserver getViewTreeObserver() { return new ViewTreeObserverImpl(); }
    public void startAnimation(wordpressa._3.mocks.android.view.animation.Animation animation) {}
    
    private static class ViewTreeObserverImpl implements ViewTreeObserver {
        public void addOnGlobalLayoutListener(OnGlobalLayoutListener l) {}
        public void removeOnGlobalLayoutListener(OnGlobalLayoutListener l) {}
        public void removeGlobalOnLayoutListener(OnGlobalLayoutListener l) {}
    }
    
    public interface OnClickListener {
        void onClick(View v);
    }
    
    public interface OnLongClickListener {
        boolean onLongClick(View v);
    }
    
    public interface OnTouchListener {
        boolean onTouch(View v, MotionEvent event);
    }
    
    public interface OnFocusChangeListener {
        void onFocusChange(View v, boolean hasFocus);
    }
}
