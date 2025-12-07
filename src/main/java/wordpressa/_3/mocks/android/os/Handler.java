package wordpressa._3.mocks.android.os;

public class Handler {
    private Looper looper;
    
    public Handler() {}
    public Handler(Looper looper) { this.looper = looper; }
    
    public boolean post(Runnable r) {
        if (r != null) r.run();
        return true;
    }
    
    public boolean postDelayed(Runnable r, long delayMillis) {
        if (r != null) r.run();
        return true;
    }
    
    public void removeCallbacks(Runnable r) {}
    public void removeCallbacksAndMessages(Object token) {}
    
    public boolean sendEmptyMessage(int what) { return true; }
    public boolean sendMessage(Message msg) { return true; }
    public boolean sendMessageDelayed(Message msg, long delayMillis) { return true; }
    
    public Looper getLooper() { return looper != null ? looper : Looper.getMainLooper(); }
}
