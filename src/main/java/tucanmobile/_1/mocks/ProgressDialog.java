package tucanmobile._1.mocks;

/**
 * Mock for android.app.ProgressDialog - stub implementation for testing.
 */
public class ProgressDialog {
    private boolean showing = true;
    private String title;
    
    public static ProgressDialog show(Object context, String title, String message, boolean indeterminate) {
        ProgressDialog dialog = new ProgressDialog();
        dialog.title = title;
        return dialog;
    }
    
    public boolean isShowing() {
        return showing;
    }
    
    public void setShowing(boolean showing) {
        this.showing = showing;
    }
    
    public void dismiss() {
        if (!showing) {
            throw new IllegalArgumentException("Dialog is not showing - cannot dismiss");
        }
        showing = false;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
}
