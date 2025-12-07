package wordpressa._3.mocks.android.text.style;

public class QuoteSpan extends CharacterStyle {
    private int color;
    
    public QuoteSpan() {}
    public QuoteSpan(int color) { this.color = color; }
    
    public int getColor() { return color; }
}
