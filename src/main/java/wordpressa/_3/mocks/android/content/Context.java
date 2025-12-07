package wordpressa._3.mocks.android.content;

import wordpressa._3.mocks.android.content.res.Resources;
import wordpressa._3.mocks.android.app.Intent;

public class Context {
    public static final String INPUT_METHOD_SERVICE = "input_method";
    public static final String CLIPBOARD_SERVICE = "clipboard";
    
    public Object getSystemService(String name) { return null; }
    public String getString(int resId) { return ""; }
    public Resources getResources() { return null; }
    public ContentResolver getContentResolver() { return new ContentResolver(); }
    public Context getApplicationContext() { return this; }
    public void startActivity(Intent intent) {}
}
