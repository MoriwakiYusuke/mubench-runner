package rhino._1.mocks;

public class FunctionNode extends ScriptOrFnNode {
    public static final int FUNCTION_STATEMENT = 1;
    public static final int FUNCTION_EXPRESSION = 2;
    public static final int FUNCTION_EXPRESSION_STATEMENT = 3;
    
    public boolean itsIgnoreDynamicScope;
    
    public String getFunctionName() { return ""; }
    public int getFunctionType() { return 0; }
    public void setFunctionType(int type) {}
    public boolean requiresActivation() { return false; }
    public void setRequiresActivation(boolean value) {}
    public void addParam(String name) {}
    public void addVar(String name) {}
    public String[] getParamAndVarNames() { return new String[0]; }
}
