package lnreadera._2.mocks;

public class KeyEvent {
    public static final int KEYCODE_BACK = 4;
    public static final int KEYCODE_MENU = 82;
    public static final int KEYCODE_VOLUME_UP = 24;
    public static final int KEYCODE_VOLUME_DOWN = 25;
    
    public static final int ACTION_UP = 1;
    public static final int ACTION_DOWN = 0;
    
    private int keyCode;
    private int action;
    
    public KeyEvent(int action, int keyCode) {
        this.action = action;
        this.keyCode = keyCode;
    }
    
    public int getKeyCode() {
        return keyCode;
    }
    
    public int getAction() {
        return action;
    }
}
