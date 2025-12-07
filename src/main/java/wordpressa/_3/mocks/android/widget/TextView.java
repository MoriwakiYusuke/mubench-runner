package wordpressa._3.mocks.android.widget;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.android.view.View;
import wordpressa._3.mocks.android.text.Editable;
import wordpressa._3.mocks.android.text.Layout;

public class TextView extends View {
    private CharSequence text = "";
    private int textColor;
    private float textSize;
    private int inputType;
    private OnEditorActionListener editorActionListener;
    
    public TextView() { super(); }
    public TextView(Context context) { super(context); }
    
    public CharSequence getText() { return text; }
    public void setText(CharSequence text) { this.text = text != null ? text : ""; }
    public void setText(int resid) { this.text = ""; }
    
    public void setTextColor(int color) { this.textColor = color; }
    public int getTextColor() { return textColor; }
    
    public void setTextSize(float size) { this.textSize = size; }
    public void setTextSize(int unit, float size) { this.textSize = size; }
    public float getTextSize() { return textSize; }
    
    public void setHint(CharSequence hint) {}
    public void setHint(int resid) {}
    public CharSequence getHint() { return ""; }
    
    public void setInputType(int type) { this.inputType = type; }
    public int getInputType() { return inputType; }
    
    public void setGravity(int gravity) {}
    public void setTypeface(android.graphics.Typeface tf) {}
    public void setTypeface(android.graphics.Typeface tf, int style) {}
    
    public int length() { return text.length(); }
    public void setLines(int lines) {}
    public void setMaxLines(int maxlines) {}
    public void setSingleLine(boolean singleLine) {}
    
    public void setMovementMethod(Object movement) {}
    public void setKeyListener(Object input) {}
    
    public void setError(CharSequence error) {}
    public CharSequence getError() { return null; }
    
    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {}
    public void setCompoundDrawablePadding(int pad) {}
    
    public void setOnEditorActionListener(OnEditorActionListener l) {
        this.editorActionListener = l;
    }
    
    public Layout getLayout() { return new Layout(); }
    
    public interface OnEditorActionListener {
        boolean onEditorAction(TextView v, int actionId, wordpressa._3.mocks.android.view.KeyEvent event);
    }
}
