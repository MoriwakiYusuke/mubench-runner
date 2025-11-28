package lnreadera._2.mocks;

public class TextView extends View {
    private String text = "";
    
    public void setText(String text) {
        this.text = text;
    }
    
    public void setText(int resId) {}
    
    public CharSequence getText() {
        return text;
    }
    
    public void setTextColor(int color) {}
    
    public void setTextSize(float size) {}
}
