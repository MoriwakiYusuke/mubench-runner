package wordpressa._3.mocks.android.text;

public class Selection {
    public static final Object SELECTION_START = new Object();
    public static final Object SELECTION_END = new Object();
    
    public static int getSelectionStart(CharSequence text) {
        if (text instanceof Spannable) {
            return ((Spannable) text).getSpanStart(SELECTION_START);
        }
        return -1;
    }
    
    public static int getSelectionEnd(CharSequence text) {
        if (text instanceof Spannable) {
            return ((Spannable) text).getSpanEnd(SELECTION_END);
        }
        return -1;
    }
    
    public static void setSelection(Spannable text, int start, int stop) {
        text.setSpan(SELECTION_START, start, start, Spannable.SPAN_POINT_MARK);
        text.setSpan(SELECTION_END, stop, stop, Spannable.SPAN_MARK_POINT);
    }
    
    public static void setSelection(Spannable text, int index) {
        setSelection(text, index, index);
    }
    
    public static void selectAll(Spannable text) {
        setSelection(text, 0, text.length());
    }
    
    public static void removeSelection(Spannable text) {
        text.removeSpan(SELECTION_START);
        text.removeSpan(SELECTION_END);
    }
}
