package wordpressa._3.mocks.android.text;

public class SpannableStringBuilder implements Spannable, Editable, CharSequence {
    private StringBuilder sb = new StringBuilder();
    private java.util.List<SpanInfo> spans = new java.util.ArrayList<>();
    
    private static class SpanInfo {
        Object what;
        int start;
        int end;
        int flags;
    }
    
    public SpannableStringBuilder() {}
    public SpannableStringBuilder(CharSequence text) { sb.append(text); }
    public SpannableStringBuilder(CharSequence text, int start, int end) { sb.append(text, start, end); }
    
    @Override public int length() { return sb.length(); }
    @Override public char charAt(int index) { return sb.charAt(index); }
    @Override public CharSequence subSequence(int start, int end) { return sb.subSequence(start, end); }
    @Override public String toString() { return sb.toString(); }
    
    @Override
    public Editable replace(int st, int en, CharSequence source) {
        sb.replace(st, en, source.toString());
        return this;
    }
    
    @Override
    public Editable replace(int st, int en, CharSequence source, int start, int end) {
        sb.replace(st, en, source.subSequence(start, end).toString());
        return this;
    }
    
    @Override
    public Editable insert(int where, CharSequence text) {
        sb.insert(where, text);
        return this;
    }
    
    @Override
    public Editable insert(int where, CharSequence text, int start, int end) {
        sb.insert(where, text.subSequence(start, end));
        return this;
    }
    
    @Override
    public Editable delete(int st, int en) {
        sb.delete(st, en);
        return this;
    }
    
    @Override
    public Editable append(CharSequence text) {
        sb.append(text);
        return this;
    }
    
    @Override
    public Editable append(CharSequence text, int start, int end) {
        sb.append(text, start, end);
        return this;
    }
    
    @Override
    public Editable append(char text) {
        sb.append(text);
        return this;
    }
    
    @Override
    public void clear() {
        sb.setLength(0);
        spans.clear();
    }
    
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
