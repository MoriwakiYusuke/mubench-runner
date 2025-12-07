package wordpressa._3.mocks.android.widget;

import wordpressa._3.mocks.android.view.View;

public class CompoundButton extends View {
    private boolean checked;
    private OnCheckedChangeListener listener;
    
    public boolean isChecked() { return checked; }
    public void setChecked(boolean checked) {
        this.checked = checked;
        if (listener != null) listener.onCheckedChanged(this, checked);
    }
    public void toggle() { setChecked(!checked); }
    
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.listener = listener;
    }
    
    public interface OnCheckedChangeListener {
        void onCheckedChanged(CompoundButton buttonView, boolean isChecked);
    }
}
