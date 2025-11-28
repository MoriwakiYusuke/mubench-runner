package lnreadera._2.mocks;

public class View {
    public static final int VISIBLE = 0;
    public static final int INVISIBLE = 4;
    public static final int GONE = 8;
    
    protected int visibility = VISIBLE;
    
    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }
    
    public int getVisibility() {
        return visibility;
    }
    
    public void setOnClickListener(OnClickListener listener) {}
    
    public void setOnTouchListener(OnTouchListener listener) {}
    
    public void setTag(Object tag) {}
    
    public Object getTag() {
        return null;
    }
    
    public int getId() {
        return 0;
    }
    
    public interface OnClickListener {
        void onClick(View v);
    }
    
    public interface OnTouchListener {
        boolean onTouch(View v, Object event);
    }
}
