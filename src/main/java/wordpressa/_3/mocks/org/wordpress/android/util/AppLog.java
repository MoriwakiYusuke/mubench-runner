package wordpressa._3.mocks.org.wordpress.android.util;

import wordpressa._3.mocks.android.content.Context;

public class AppLog {
    public enum T {
        READER, POSTS, MEDIA, EDITOR, COMMENTS, NOTIFS, STATS, UTILS, PROFILING, API, DB, SUGGESTION
    }
    
    public static void v(T tag, String message) {}
    public static void d(T tag, String message) {}
    public static void i(T tag, String message) {}
    public static void w(T tag, String message) {}
    public static void e(T tag, String message) {}
    public static void e(T tag, String message, Throwable tr) {}
    public static void e(T tag, Throwable tr) {}
}
