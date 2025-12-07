package wordpressa._3.mocks.android.view;

public class Window {
    public static final int FEATURE_NO_TITLE = 1;
    public static final int FEATURE_INDETERMINATE_PROGRESS = 5;
    
    public void setFlags(int flags, int mask) {}
    public void addFlags(int flags) {}
    public void clearFlags(int flags) {}
    public View getDecorView() { return new View(); }
    public void setSoftInputMode(int mode) {}
}
