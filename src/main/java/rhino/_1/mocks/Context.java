package rhino._1.mocks;

public class Context {
    public static final int VERSION_1_2 = 120;
    public static final int VERSION_1_3 = 130;
    
    public static RuntimeException reportRuntimeError(String message, String sourceName, int lineno, String lineSource, int offset) {
        return new RuntimeException(message);
    }
    public static RuntimeException reportRuntimeError(String message) {
        return new RuntimeException(message);
    }
}
