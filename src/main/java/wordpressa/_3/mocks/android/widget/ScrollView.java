package wordpressa._3.mocks.android.widget;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.android.view.ViewGroup;

public class ScrollView extends FrameLayout {
    public ScrollView() { super(); }
    public ScrollView(Context context) { super(context); }
    
    public void smoothScrollTo(int x, int y) {}
    public void fullScroll(int direction) {}
    public void scrollTo(int x, int y) {}
}
