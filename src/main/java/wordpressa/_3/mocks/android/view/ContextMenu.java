package wordpressa._3.mocks.android.view;

public class ContextMenu extends Menu {
    private ContextMenuInfo menuInfo;
    
    public void setHeaderTitle(CharSequence title) {}
    public void setHeaderTitle(int titleRes) {}
    public void setHeaderIcon(int iconRes) {}
    public void setHeaderView(View view) {}
    
    public ContextMenuInfo getMenuInfo() { return menuInfo; }
    public void setMenuInfo(ContextMenuInfo menuInfo) { this.menuInfo = menuInfo; }
    
    public interface ContextMenuInfo {
    }
}
