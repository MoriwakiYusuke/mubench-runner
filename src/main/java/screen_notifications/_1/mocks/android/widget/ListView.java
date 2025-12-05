package screen_notifications._1.mocks.android.widget;

import screen_notifications._1.mocks.android.view.View;

/**
 * Stub for Android's ListView class.
 */
public class ListView extends View {
    private ListAdapter adapter;

    public void setAdapter(ListAdapter adapter) {
        this.adapter = adapter;
    }

    public ListAdapter getAdapter() {
        return adapter;
    }
}
