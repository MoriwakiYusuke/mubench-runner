package rhino._1.mocks;

public class ScriptRuntime {
    public static String getMessage0(String messageId) { return messageId; }
    public static String getMessage1(String messageId, String arg) { return messageId + ": " + arg; }
    public static String getMessage2(String messageId, String arg1, String arg2) { return messageId; }
    public static Object getIndexObject(String s) { return s; }
    public static Object getIndexObject(double n) { return n; }
}
