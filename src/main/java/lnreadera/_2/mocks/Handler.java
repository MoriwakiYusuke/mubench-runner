package lnreadera._2.mocks;

public class Handler {
    public Handler() {}
    
    public boolean post(Runnable r) {
        return true;
    }
    
    public boolean postDelayed(Runnable r, long delayMillis) {
        return true;
    }
    
    public void removeCallbacks(Runnable r) {}
}
