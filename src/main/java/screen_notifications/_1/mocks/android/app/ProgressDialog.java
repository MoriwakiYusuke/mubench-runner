package screen_notifications._1.mocks.android.app;

import screen_notifications._1.mocks.android.content.Context;

/**
 * Stub for Android's ProgressDialog class.
 */
public class ProgressDialog {
    private boolean showing = true;

    public ProgressDialog() {
    }

    public static ProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate) {
        return new ProgressDialog();
    }

    public boolean isShowing() {
        return showing;
    }

    public void cancel() {
        showing = false;
    }
}
