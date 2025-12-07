package wordpressa._3.mocks.android.text.style;

import wordpressa._3.mocks.android.graphics.Typeface;

public class StyleSpan extends CharacterStyle {
    private int style;
    
    public StyleSpan(int style) {
        this.style = style;
    }
    
    public int getStyle() {
        return style;
    }
}
