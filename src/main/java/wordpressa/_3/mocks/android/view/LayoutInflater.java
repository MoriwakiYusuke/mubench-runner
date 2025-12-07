package wordpressa._3.mocks.android.view;

import wordpressa._3.mocks.android.content.Context;

public class LayoutInflater {
    private Context context;
    
    public LayoutInflater(Context context) {
        this.context = context;
    }
    
    public static LayoutInflater from(Context context) {
        return new LayoutInflater(context);
    }
    
    public View inflate(int resource, ViewGroup root) {
        return new View(context);
    }
    
    public View inflate(int resource, ViewGroup root, boolean attachToRoot) {
        return new View(context);
    }
}
