package wordpressa._3.mocks.org.wordpress.android.ui.posts;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.android.text.Editable;
import wordpressa._3.mocks.android.text.SpannableStringBuilder;
import wordpressa._3.mocks.android.widget.EditText;

public class WPEditText extends EditText {
    private boolean jsInterfaceAddedFlag = false;
    
    public WPEditText() { super(); }
    public WPEditText(Context context) { super(context); }
    
    public boolean isJsInterfaceAddedFlag() { return jsInterfaceAddedFlag; }
    public void setJsInterfaceAddedFlag(boolean flag) { this.jsInterfaceAddedFlag = flag; }
    
    // Override getText to support null text simulation
    private Editable nullableText;
    private boolean returnNull = false;
    
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
