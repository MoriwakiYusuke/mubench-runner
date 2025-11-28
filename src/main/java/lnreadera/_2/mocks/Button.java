package lnreadera._2.mocks;

public class Button extends View {
    private String text = "";
    
    public void setText(String text) {
        this.text = text;
    }
    
    public void setText(int resId) {}
    
    public CharSequence getText() {
        return text;
    }
    
    public void setEnabled(boolean enabled) {}
}
