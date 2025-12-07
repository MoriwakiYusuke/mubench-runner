package wordpressa._3.mocks.android.text.style;

public class SuggestionSpan {
    public static final int FLAG_EASY_CORRECT = 0x0001;
    public static final int FLAG_MISSPELLED = 0x0002;
    
    public SuggestionSpan(android.content.Context context, String[] suggestions, int flags) {}
    
    public String[] getSuggestions() { return new String[0]; }
    public int getFlags() { return 0; }
}
