package wordpressa._3.mocks.org.wordpress.android.util;

import wordpressa._3.mocks.android.content.Context;

public class DisplayUtils {
    public static int dpToPx(Context context, int dp) { return dp * 2; }
    public static int pxToDp(Context context, int px) { return px / 2; }
    public static int getDisplayPixelWidth(Context context) { return 1080; }
    public static int getDisplayPixelHeight(Context context) { return 1920; }
    public static boolean isLandscape(Context context) { return false; }
}
