package wordpressa._1.mocks;

/**
 * Mock for android.widget.TextView
 */
public class TextView extends View {
    private CharSequence text;
    
    public void setText(CharSequence text) {
        this.text = text;
    }
    
    public CharSequence getText() {
        return text;
    }
}
