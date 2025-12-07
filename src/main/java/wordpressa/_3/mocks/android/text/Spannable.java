package wordpressa._3.mocks.android.text;

public interface Spannable extends Spanned {
    int SPAN_EXCLUSIVE_EXCLUSIVE = 33;
    int SPAN_INCLUSIVE_EXCLUSIVE = 17;
    int SPAN_EXCLUSIVE_INCLUSIVE = 34;
    int SPAN_INCLUSIVE_INCLUSIVE = 18;
    int SPAN_POINT_MARK = 0x11;
    int SPAN_MARK_POINT = 0x22;
    
    void setSpan(Object what, int start, int end, int flags);
    void removeSpan(Object what);
}
