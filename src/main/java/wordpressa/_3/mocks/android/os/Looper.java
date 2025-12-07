package wordpressa._3.mocks.android.os;

public class Looper {
    private static Looper mainLooper = new Looper();
    
    public static Looper getMainLooper() { return mainLooper; }
    public static Looper myLooper() { return mainLooper; }
    public static void prepare() {}
    public static void loop() {}
    public void quit() {}
}
