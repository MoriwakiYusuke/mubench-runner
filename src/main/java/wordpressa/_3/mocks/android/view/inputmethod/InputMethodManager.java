package wordpressa._3.mocks.android.view.inputmethod;

import wordpressa._3.mocks.android.view.View;

public class InputMethodManager {
    public static final int SHOW_FORCED = 2;
    public static final int SHOW_IMPLICIT = 1;
    public static final int HIDE_IMPLICIT_ONLY = 1;
    public static final int HIDE_NOT_ALWAYS = 2;
    public static final int RESULT_UNCHANGED_SHOWN = 0;
    public static final int RESULT_HIDDEN = 2;
    public static final int RESULT_SHOWN = 1;
    
    public void showSoftInput(View view, int flags) {}
    public boolean hideSoftInputFromWindow(Object windowToken, int flags) { return true; }
    public void toggleSoftInput(int showFlags, int hideFlags) {}
    public boolean isActive() { return false; }
    public boolean isActive(View view) { return false; }
}
