package wordpressa._3.mocks.org.wordpress.android.util;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.android.widget.Toast;

public class ToastUtils {
    
    public static void showToast(Context context, String message) {}
    public static void showToast(Context context, String message, int duration) {}
    public static void showToast(Context context, int resId) {}
    public static void showToast(Context context, int resId, int duration) {}
    
    public enum Duration {
        SHORT, LONG
    }
}
