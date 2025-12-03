package rhino._1.mocks;

import java.io.Reader;

public class TokenStream {
    public String regExpFlags;
    
    public TokenStream(Object parser, Reader reader, String source, int lineno) {}
    public int getToken() { return 0; }
    public int getLineno() { return 0; }
    public String getString() { return ""; }
    public double getNumber() { return 0; }
    public int getTokenno() { return 0; }
    public String getLine() { return ""; }
    public int getOffset() { return 0; }
    public boolean eof() { return true; }
    public int getOp() { return 0; }
    public void reportSyntaxError(String message, boolean fatal) {}
    public String getSourceString() { return ""; }
    public int getCursor() { return 0; }
    public void readRegExp(int token) {}
    public String getRegExpFlags() { return ""; }
    public boolean isXMLAttribute() { return false; }
    public int getFirstXMLToken() { return 0; }
    public int getNextXMLToken() { return 0; }
    public String getString(boolean b) { return ""; }
}
