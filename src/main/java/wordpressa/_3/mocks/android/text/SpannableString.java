package wordpressa._3.mocks.android.text;

public class SpannableString implements Spannable, CharSequence {
    private String text;
    private java.util.List<SpanInfo> spans = new java.util.ArrayList<>();
    
    private static class SpanInfo {
        Object what;
        int start;
        int end;
        int flags;
    }
    
    public SpannableString(CharSequence source) {
        this.text = source.toString();
    }
    
    @Override public int length() { return text.length(); }
    @Override public char charAt(int index) { return text.charAt(index); }
    @Override public CharSequence subSequence(int start, int end) { return text.subSequence(start, end); }
    @Override public String toString() { return text; }
    
    @Override
    public void setSpan(Object what, int start, int end, int flags) {
        SpanInfo info = new SpanInfo();
        info.what = what;
        info.start = start;
        info.end = end;
        info.flags = flags;
        spans.add(info);
    }
    
    @Override
    public void removeSpan(Object what) {
        spans.removeIf(s -> s.what == what);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] getSpans(int start, int end, Class<T> type) {
        java.util.List<Object> result = new java.util.ArrayList<>();
        for (SpanInfo s : spans) {
            if (type.isInstance(s.what) && s.start <= end && s.end >= start) {
                result.add(s.what);
            }
        }
        return (T[]) result.toArray((T[]) java.lang.reflect.Array.newInstance(type, result.size()));
    }
    
    @Override
    public int getSpanStart(Object tag) {
        for (SpanInfo s : spans) {
            if (s.what == tag) return s.start;
        }
        return -1;
    }
    
    @Override
    public int getSpanEnd(Object tag) {
        for (SpanInfo s : spans) {
            if (s.what == tag) return s.end;
        }
        return -1;
    }
    
    @Override
    public int getSpanFlags(Object tag) {
        for (SpanInfo s : spans) {
            if (s.what == tag) return s.flags;
        }
        return 0;
    }
    
    @Override
    public int nextSpanTransition(int start, int limit, Class type) {
        return limit;
    }
}
