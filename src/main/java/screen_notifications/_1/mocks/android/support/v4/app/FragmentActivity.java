package screen_notifications._1.mocks.android.support.v4.app;

import screen_notifications._1.mocks.android.content.Context;
import screen_notifications._1.mocks.android.os.Bundle;
import screen_notifications._1.mocks.android.view.Menu;
import screen_notifications._1.mocks.android.view.MenuInflater;
import screen_notifications._1.mocks.android.view.MenuItem;
import screen_notifications._1.mocks.android.view.View;
import screen_notifications._1.mocks.android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

/**
 * Stub for Android's FragmentActivity class.
 */
public class FragmentActivity extends Context {
    private LoaderManager loaderManager = new LoaderManager();
    private MenuInflater menuInflater = new MenuInflater();
    private Map<Integer, View> views = new HashMap<>();
    private int contentViewId;

    public FragmentActivity() {
        // Pre-register a ListView for R.id.appsList (typically 1)
        views.put(1, new ListView());
    }

    public void onCreate(Bundle savedInstanceState) {
        // Stub implementation
    }

    public void setContentView(int layoutResId) {
        this.contentViewId = layoutResId;
    }

    public LoaderManager getSupportLoaderManager() {
        return loaderManager;
    }

    public MenuInflater getMenuInflater() {
        return menuInflater;
    }

    public View findViewById(int id) {
        return views.computeIfAbsent(id, k -> new View());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
