package lnreadera._2.mocks;

public class Toast {
    public static final int LENGTH_SHORT = 0;
    public static Toast makeText(Object context, String text, int duration) {
        return new Toast();
    }
    public void show() {}
}
