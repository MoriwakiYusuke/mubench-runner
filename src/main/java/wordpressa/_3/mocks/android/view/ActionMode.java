package wordpressa._3.mocks.android.view;

public class ActionMode {
    private Callback callback;
    
    public void finish() {}
    public void invalidate() {}
    public void setTitle(CharSequence title) {}
    public void setTitle(int resId) {}
    public void setSubtitle(CharSequence subtitle) {}
    public Menu getMenu() { return new Menu(); }
    public MenuInflater getMenuInflater() { return null; }
    
    public interface Callback {
        boolean onCreateActionMode(ActionMode mode, Menu menu);
        boolean onPrepareActionMode(ActionMode mode, Menu menu);
        boolean onActionItemClicked(ActionMode mode, MenuItem item);
        void onDestroyActionMode(ActionMode mode);
    }
}
