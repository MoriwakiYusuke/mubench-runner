package lnreadera._2.mocks;

public class ProgressDialog {
    public static ProgressDialog show(Object context, String title, String message, boolean indeterminate) {
        return new ProgressDialog();
    }
    public Window getWindow() { return new Window(); }
    public void setCanceledOnTouchOutside(boolean cancel) {}
    public void dismiss() {}
    public boolean isShowing() { return false; }
    public void setMessage(String message) {}
    public void setIndeterminate(boolean indeterminate) {}
    public void setSecondaryProgress(int progress) {}
    public void setMax(int max) {}
    public void setProgress(int progress) {}
}
