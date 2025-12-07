package wordpressa._3.mocks.android.view;

public class KeyEvent {
    public static final int KEYCODE_ENTER = 66;
    public static final int KEYCODE_DEL = 67;
    public static final int KEYCODE_TAB = 61;
    
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_UP = 1;
    
    private int action;
    private int keyCode;
    
    public int getAction() { return action; }
    public int getKeyCode() { return keyCode; }
    
    public KeyEvent(int action, int code) {
        this.action = action;
        this.keyCode = code;
    }
}
