package wordpressa._3.mocks.android.widget;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.android.view.View;

public class ToggleButton extends View {
    private boolean checked;
    
    public ToggleButton() { super(); }
    public ToggleButton(Context context) { super(context); }
    
    public boolean isChecked() { return checked; }
    public void setChecked(boolean checked) { this.checked = checked; }
    public void toggle() { checked = !checked; }
    
    public void setTextOn(CharSequence textOn) {}
    public void setTextOff(CharSequence textOff) {}
}
