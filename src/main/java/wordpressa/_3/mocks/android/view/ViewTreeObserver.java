package wordpressa._3.mocks.android.view;

public interface ViewTreeObserver {
    void addOnGlobalLayoutListener(OnGlobalLayoutListener listener);
    void removeOnGlobalLayoutListener(OnGlobalLayoutListener listener);
    void removeGlobalOnLayoutListener(OnGlobalLayoutListener victim);
    
    interface OnGlobalLayoutListener {
        void onGlobalLayout();
    }
    
    interface OnPreDrawListener {
        boolean onPreDraw();
    }
    
    interface OnScrollChangedListener {
        void onScrollChanged();
    }
}
