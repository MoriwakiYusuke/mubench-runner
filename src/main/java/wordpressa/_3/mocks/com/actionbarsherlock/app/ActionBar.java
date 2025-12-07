package wordpressa._3.mocks.com.actionbarsherlock.app;

public class ActionBar {
    public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {}
    public void setTitle(CharSequence title) {}
    public void setTitle(int resId) {}
    public void setSubtitle(CharSequence subtitle) {}
    public void setSubtitle(int resId) {}
    public void setDisplayShowTitleEnabled(boolean showTitle) {}
    public void setDisplayShowHomeEnabled(boolean showHome) {}
    public void setHomeButtonEnabled(boolean enabled) {}
    public void setNavigationMode(int mode) {}
    public void hide() {}
    public void show() {}
    public boolean isShowing() { return true; }
    
    public static final int NAVIGATION_MODE_STANDARD = 0;
    public static final int NAVIGATION_MODE_LIST = 1;
    public static final int NAVIGATION_MODE_TABS = 2;
}
