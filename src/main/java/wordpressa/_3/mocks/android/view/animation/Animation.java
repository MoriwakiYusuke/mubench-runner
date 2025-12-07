package wordpressa._3.mocks.android.view.animation;

public class Animation {
    public static final int RELATIVE_TO_SELF = 1;
    public static final int RELATIVE_TO_PARENT = 2;
    public static final int ABSOLUTE = 0;
    public static final int INFINITE = -1;
    
    public void setDuration(long durationMillis) {}
    public void setFillAfter(boolean fillAfter) {}
    public void setFillBefore(boolean fillBefore) {}
    public void setRepeatCount(int repeatCount) {}
    public void setInterpolator(android.view.animation.Interpolator i) {}
    public void setAnimationListener(AnimationListener listener) {}
    
    public interface AnimationListener {
        void onAnimationStart(Animation animation);
        void onAnimationEnd(Animation animation);
        void onAnimationRepeat(Animation animation);
    }
}
