package rhino._1.mocks;

public class Decompiler {
    public static final int ONLY_BODY_FLAG = 1;
    public static final int TO_SOURCE_FLAG = 2;
    
    public String getEncodedSource() { return ""; }
    public void addToken(int token) {}
    public void addName(String name) {}
    public void addString(String s) {}
    public void addNumber(double n) {}
    public void addRegexp(String regexp, String flags) {}
    public void addEOL(int token) {}
    public int getCurrentOffset() { return 0; }
    public int markFunctionStart(int type) { return 0; }
    public int markFunctionEnd(int startPos) { return 0; }
}
