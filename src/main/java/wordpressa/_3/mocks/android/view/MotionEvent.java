package wordpressa._3.mocks.android.view;

public class MotionEvent {
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_UP = 1;
    public static final int ACTION_MOVE = 2;
    public static final int ACTION_CANCEL = 3;
    
    private int action;
    private float x;
    private float y;
    
    public int getAction() { return action; }
    public float getX() { return x; }
    public float getY() { return y; }
    public float getRawX() { return x; }
    public float getRawY() { return y; }
    
    public static MotionEvent obtain(long downTime, long eventTime, int action, float x, float y, int metaState) {
        MotionEvent e = new MotionEvent();
        e.action = action;
        e.x = x;
        e.y = y;
        return e;
    }
    
    public void recycle() {}
}
