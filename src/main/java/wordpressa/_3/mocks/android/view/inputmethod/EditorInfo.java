package wordpressa._3.mocks.android.view.inputmethod;

public class EditorInfo {
    public static final int IME_ACTION_DONE = 6;
    public static final int IME_ACTION_GO = 2;
    public static final int IME_ACTION_NEXT = 5;
    public static final int IME_ACTION_NONE = 1;
    public static final int IME_ACTION_PREVIOUS = 7;
    public static final int IME_ACTION_SEARCH = 3;
    public static final int IME_ACTION_SEND = 4;
    public static final int IME_ACTION_UNSPECIFIED = 0;
    
    public static final int IME_FLAG_NO_EXTRACT_UI = 0x10000000;
    public static final int IME_FLAG_NO_FULLSCREEN = 0x02000000;
    
    public int inputType;
    public int imeOptions;
    public CharSequence hintText;
    public CharSequence label;
    public CharSequence actionLabel;
    public int actionId;
}
