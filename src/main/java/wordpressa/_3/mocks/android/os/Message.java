package wordpressa._3.mocks.android.os;

public class Message {
    public int what;
    public int arg1;
    public int arg2;
    public Object obj;
    
    public static Message obtain() { return new Message(); }
    public static Message obtain(Handler h, int what) {
        Message m = new Message();
        m.what = what;
        return m;
    }
    
    public void setTarget(Handler target) {}
    public void sendToTarget() {}
}
