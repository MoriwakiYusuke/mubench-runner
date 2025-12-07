package wordpressa._3.mocks.org.wordpress.android.util;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.android.text.Editable;
import wordpressa._3.mocks.android.text.SpannableStringBuilder;
import wordpressa._3.mocks.android.widget.EditText;

public class WPEditText extends EditText {
    private OnSelectionChangedListener listener;
    private EditTextImeBackListener imeBackListener;
    private boolean jsInterfaceAddedFlag = false;
    
    // To support null text simulation
    private boolean returnNull = false;
    
    public WPEditText() { super(); }
    public WPEditText(Context context) { super(context); }
    
    public interface OnSelectionChangedListener {
        void onSelectionChanged();
    }
    
    public interface EditTextImeBackListener {
        void onImeBack(WPEditText ctrl, String text);
    }
    
    public void setOnSelectionChangedListener(OnSelectionChangedListener listener) {
        this.listener = listener;
    }
    
    public void setOnEditTextImeBackListener(EditTextImeBackListener listener) {
        this.imeBackListener = listener;
    }
    
    public void triggerSelectionChanged() {
        if (listener != null) listener.onSelectionChanged();
    }
    
    public boolean isJsInterfaceAddedFlag() { return jsInterfaceAddedFlag; }
    public void setJsInterfaceAddedFlag(boolean flag) { this.jsInterfaceAddedFlag = flag; }
    
    public void setReturnNullText(boolean returnNull) {
        this.returnNull = returnNull;
    }
    
    @Override
    public Editable getText() {
        if (returnNull) {
            return null;
        }
        return super.getText();
    }
}
