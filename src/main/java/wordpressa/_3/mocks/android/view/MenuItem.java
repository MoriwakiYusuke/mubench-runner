package wordpressa._3.mocks.android.view;

public class MenuItem {
    public static final int SHOW_AS_ACTION_NEVER = 0;
    public static final int SHOW_AS_ACTION_IF_ROOM = 1;
    public static final int SHOW_AS_ACTION_ALWAYS = 2;
    
    private int itemId;
    private CharSequence title;
    private boolean enabled = true;
    private boolean visible = true;
    
    public int getItemId() { return itemId; }
    public MenuItem setItemId(int id) { this.itemId = id; return this; }
    
    public CharSequence getTitle() { return title; }
    public MenuItem setTitle(CharSequence title) { this.title = title; return this; }
    public MenuItem setTitle(int title) { return this; }
    
    public boolean isEnabled() { return enabled; }
    public MenuItem setEnabled(boolean enabled) { this.enabled = enabled; return this; }
    
    public boolean isVisible() { return visible; }
    public MenuItem setVisible(boolean visible) { this.visible = visible; return this; }
    
    public MenuItem setShowAsAction(int actionEnum) { return this; }
    public MenuItem setIcon(int iconRes) { return this; }
    public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener listener) { return this; }
    
    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem item);
    }
}
