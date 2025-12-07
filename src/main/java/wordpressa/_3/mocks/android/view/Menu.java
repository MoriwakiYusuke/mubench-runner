package wordpressa._3.mocks.android.view;

public class Menu {
    public MenuItem add(CharSequence title) { return new MenuItem().setTitle(title); }
    public MenuItem add(int groupId, int itemId, int order, CharSequence title) { 
        return new MenuItem().setItemId(itemId).setTitle(title); 
    }
    public MenuItem add(int groupId, int itemId, int order, int titleRes) {
        return new MenuItem().setItemId(itemId);
    }
    public MenuItem findItem(int id) { return new MenuItem().setItemId(id); }
    public void removeItem(int id) {}
    public void clear() {}
    public int size() { return 0; }
    public MenuItem getItem(int index) { return new MenuItem(); }
}
