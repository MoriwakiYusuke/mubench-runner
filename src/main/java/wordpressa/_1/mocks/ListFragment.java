package wordpressa._1.mocks;

/**
 * Mock for android.app.ListFragment - core class for testing
 */
public class ListFragment {
    private boolean isAdded = true;
    private ListView listView;
    private Activity activity;
    private Object listAdapter;
    
    public ListFragment() {
        this.listView = new ListView();
        this.activity = new Activity();
    }
    
    public boolean isAdded() {
        return isAdded;
    }
    
    public void setIsAdded(boolean added) {
        this.isAdded = added;
    }
    
    public ListView getListView() {
        // This should only be called when isAdded() is true
        // In the real Android, this can throw IllegalStateException if not added
        return listView;
    }
    
    public void setListView(ListView listView) {
        this.listView = listView;
    }
    
    public Activity getActivity() {
        return activity;
    }
    
    public void setListAdapter(Object adapter) {
        this.listAdapter = adapter;
    }
    
    public CharSequence getText(int resId) {
        return "Mock text for resource " + resId;
    }
    
    public void onActivityCreated(Bundle savedInstanceState) {
        // Override in subclass
    }
    
    public void onResume() {
        // Override in subclass
    }
    
    public void onPause() {
        // Override in subclass
    }
    
    public void onDestroy() {
        // Override in subclass
    }
    
    public void onSaveInstanceState(Bundle outState) {
        // Override in subclass
    }
    
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Override in subclass
    }
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new View();
    }
}
