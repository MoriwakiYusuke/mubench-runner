package wordpressa._3.mocks.android.widget;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.android.view.View;

public class Spinner extends View {
    private int selectedPosition = 0;
    private Object selectedItem;
    
    public Spinner() { super(); }
    public Spinner(Context context) { super(context); }
    
    public int getSelectedItemPosition() { return selectedPosition; }
    public Object getSelectedItem() { return selectedItem; }
    public void setSelection(int position) { this.selectedPosition = position; }
    public void setSelection(int position, boolean animate) { this.selectedPosition = position; }
    
    public void setAdapter(Object adapter) {}
    public Object getAdapter() { return null; }
    
    public void setOnItemSelectedListener(OnItemSelectedListener listener) {}
    
    public interface OnItemSelectedListener {
        void onItemSelected(Object parent, View view, int position, long id);
        void onNothingSelected(Object parent);
    }
}
