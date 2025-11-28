package lnreadera._2.mocks;

public class EditText extends View {
    private String text = "";
    private String hint = "";
    
    public void setText(String text) {
        this.text = text;
    }
    
    public void setText(int resId) {}
    
    public CharSequence getText() {
        return text;
    }
    
    public void setHint(String hint) {
        this.hint = hint;
    }
    
    public void setHint(int resId) {}
    
    public CharSequence getHint() {
        return hint;
    }
    
    public void setSelection(int index) {}
    
    public void selectAll() {}
}
