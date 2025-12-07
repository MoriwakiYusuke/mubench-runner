package wordpressa._1.mocks;

/**
 * Mock for android.widget.ListView
 */
public class ListView extends View {
    public static final int INVALID_POSITION = -1;
    
    private int firstVisiblePosition = 0;
    private int selectionFromTop = INVALID_POSITION;
    private View emptyView;
    
    public void setDivider(Object divider) {
        // no-op
    }
    
    public void setDividerHeight(int height) {
        // no-op
    }
    
    public View getEmptyView() {
        return emptyView;
    }
    
    public void setEmptyView(View view) {
        this.emptyView = view;
    }
    
    public int getFirstVisiblePosition() {
        return firstVisiblePosition;
    }
    
    public void setFirstVisiblePosition(int position) {
        this.firstVisiblePosition = position;
    }
    
    public void setSelectionFromTop(int position, int y) {
        this.selectionFromTop = position;
    }
    
    public int getSelectionFromTop() {
        return selectionFromTop;
    }
}
