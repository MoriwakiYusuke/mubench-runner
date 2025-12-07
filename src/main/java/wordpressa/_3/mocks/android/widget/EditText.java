package wordpressa._3.mocks.android.widget;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.android.text.Editable;
import wordpressa._3.mocks.android.text.Layout;
import wordpressa._3.mocks.android.text.SpannableStringBuilder;
import wordpressa._3.mocks.android.text.TextWatcher;

public class EditText extends TextView {
    private SpannableStringBuilder editableText = new SpannableStringBuilder();
    private int selectionStart = 0;
    private int selectionEnd = 0;
    private java.util.List<TextWatcher> textWatchers = new java.util.ArrayList<>();
    
    public EditText() { super(); }
    public EditText(Context context) { super(context); }
    
    @Override
    public Editable getText() {
        return editableText;
    }
    
    @Override
    public void setText(CharSequence text) {
        String oldText = editableText.toString();
        editableText.clear();
        if (text != null) {
            editableText.append(text);
        }
        super.setText(text);
        notifyTextChanged(oldText, text != null ? text.toString() : "");
    }
    
    public void setText(CharSequence text, BufferType type) {
        setText(text);
    }
    
    public int getSelectionStart() { return selectionStart; }
    public int getSelectionEnd() { return selectionEnd; }
    
    public void setSelection(int start, int stop) {
        this.selectionStart = start;
        this.selectionEnd = stop;
    }
    
    public void setSelection(int index) {
        this.selectionStart = index;
        this.selectionEnd = index;
    }
    
    public void selectAll() {
        setSelection(0, editableText.length());
    }
    
    public void extendSelection(int index) {
        this.selectionEnd = index;
    }
    
    public Layout getLayout() { return new Layout(); }
    
    public void addTextChangedListener(TextWatcher watcher) {
        textWatchers.add(watcher);
    }
    
    public void removeTextChangedListener(TextWatcher watcher) {
        textWatchers.remove(watcher);
    }
    
    private void notifyTextChanged(String oldText, String newText) {
        for (TextWatcher watcher : textWatchers) {
            watcher.beforeTextChanged(oldText, 0, oldText.length(), newText.length());
            watcher.onTextChanged(newText, 0, oldText.length(), newText.length());
            watcher.afterTextChanged(editableText);
        }
    }
    
    public enum BufferType {
        NORMAL, SPANNABLE, EDITABLE
    }
}
