package wordpressa._3.mocks.android.widget;

import wordpressa._3.mocks.android.content.Context;

public class ArrayAdapter<T> {
    public ArrayAdapter(Context context, int resource) {}
    public ArrayAdapter(Context context, int resource, T[] objects) {}
    public ArrayAdapter(Context context, int resource, java.util.List<T> objects) {}
    
    public static ArrayAdapter<CharSequence> createFromResource(Object context, int textArrayResId, int textViewResId) {
        return new ArrayAdapter<>(null, textViewResId);
    }
    
    public void add(T object) {}
    public void addAll(java.util.Collection<? extends T> collection) {}
    public void remove(T object) {}
    public void clear() {}
    public int getCount() { return 0; }
    public T getItem(int position) { return null; }
    public void notifyDataSetChanged() {}
    public void setDropDownViewResource(int resource) {}
}
