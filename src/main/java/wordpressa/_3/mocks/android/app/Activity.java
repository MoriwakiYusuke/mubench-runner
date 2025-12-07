package wordpressa._3.mocks.android.app;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.android.view.View;
import wordpressa._3.mocks.android.view.Window;
import wordpressa._3.mocks.android.os.Bundle;
import wordpressa._3.mocks.android.content.res.Resources;

public class Activity extends Context {
    public static final int RESULT_OK = -1;
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_FIRST_USER = 1;
    
    private Intent intent = new Intent();
    
    public void finish() {}
    public Activity getActivity() { return this; }
    @Override
    public Object getSystemService(String name) { return null; }
    public void startActivity(Intent intent) {}
    public void startActivityForResult(Intent intent, int requestCode) {}
    public String getString(int resId) { return ""; }
    public View findViewById(int id) { return null; }
    public Window getWindow() { return new Window(); }
    @Override
    public Resources getResources() { return null; }
    public void registerForContextMenu(View view) {}
    public void onActivityResult(int requestCode, int resultCode, Intent data) {}
    public void runOnUiThread(Runnable runnable) {
        if (runnable != null) runnable.run();
    }
    public void sendBroadcast(Intent intent) {}
    public Intent getIntent() { return intent; }
    public void setIntent(Intent intent) { this.intent = intent; }
}
