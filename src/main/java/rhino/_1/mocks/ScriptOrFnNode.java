package rhino._1.mocks;

public class ScriptOrFnNode extends Node {
    public String getSourceName() { return ""; }
    public void setSourceName(String name) {}
    public void setBaseLineno(int lineno) {}
    public void setEndLineno(int lineno) {}
    public void setEncodedSourceBounds(int start, int end) {}
    public int getEncodedSourceStart() { return 0; }
    public int getEncodedSourceEnd() { return 0; }
    public int getBaseLineno() { return 0; }
    public int getEndLineno() { return 0; }
    public int getFunctionCount() { return 0; }
    public FunctionNode getFunctionNode(int i) { return null; }
    public int getRegexpCount() { return 0; }
    public String getRegexpString(int i) { return ""; }
    public String getRegexpFlags(int i) { return ""; }
    public int addRegexp(String s, String flags) { return 0; }
    public int addFunction(FunctionNode fn) { return 0; }
    public int getParamCount() { return 0; }
    public String getParamOrVarName(int i) { return ""; }
    public int getParamAndVarCount() { return 0; }
    public int getIndexForNameNode(Node nameNode) { return -1; }
    public String getNextTempName() { return ""; }
    public void addSymbol(Object symbol) {}
    public boolean hasParamOrVar(String name) { return false; }
    public String getEncodedSource() { return ""; }
    public void addVar(String name) {}
}
