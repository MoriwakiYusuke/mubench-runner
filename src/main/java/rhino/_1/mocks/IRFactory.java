package rhino._1.mocks;

public class IRFactory {
    public IRFactory(Object parser) {}
    public ScriptOrFnNode createScript() { return new ScriptOrFnNode(); }
    public void initScript(ScriptOrFnNode scriptNode, Node body) {}
    public Node createLoopNode(Node label, int lineno) { return new Node(); }
    public Node createSwitch(Node expr, int lineno) { return new Node(); }
    public Node createLeaf(int type) { return new Node(); }
    public Node createBlock(int lineno) { return new Node(); }
    public Node createName(String name) { return new Node(); }
    public FunctionNode createFunction(String name) { return new FunctionNode(); }
    public Node initFunction(FunctionNode fn, int index, Node body, int syntheticType) { return new Node(); }
    public Node createExprStatement(Node expr, int lineno) { return new Node(); }
    public Node createExprStatementNoReturn(Node expr, int lineno) { return new Node(); }
    public Node createIf(Node cond, Node then, Node elseNode, int lineno) { return new Node(); }
    public Node createFor(Node loop, Node init, Node cond, Node incr, Node body) { return new Node(); }
    public Node createWhile(Node loop, Node cond, Node body) { return new Node(); }
    public Node createDoWhile(Node loop, Node body, Node cond) { return new Node(); }
    public Node createBreak(Node label, int lineno) { return new Node(); }
    public Node createContinue(Node label, int lineno) { return new Node(); }
    public Node createReturn(Node expr, int lineno) { return new Node(); }
    public Node createWith(Node obj, Node body, int lineno) { return new Node(); }
    public Node createArrayLiteral(ObjArray elems, int skipCount) { return new Node(); }
    public Node createObjectLiteral(ObjArray elems) { return new Node(); }
    public Node createRegExp(int index) { return new Node(); }
    public Node createString(String str) { return new Node(); }
    public Node createNumber(double num) { return new Node(); }
    public Node createCatch(String name, Node cond, Node body, int lineno) { return new Node(); }
    public Node createThrow(Node expr, int lineno) { return new Node(); }
    public Node createTryCatchFinally(Node tryBlock, Node catchBlocks, Node finallyBlock, int lineno) { return new Node(); }
    public Node createVariables(int lineno) { return new Node(); }
    public Node createAssignment(int type, Node left, Node right) { return new Node(); }
    public Node createUnary(int type, Node child) { return new Node(); }
    public Node createUnary(int type, int flags, Node child) { return new Node(); }
    public Node createBinary(int type, Node left, Node right) { return new Node(); }
    public Node createBinary(int type, int op, Node left, Node right) { return new Node(); }
    public Node createTernary(Node cond, Node thenPart, Node elsePart) { return new Node(); }
    public Node createIncDec(int type, boolean post, Node child) { return new Node(); }
    public Node createPropertyGet(Node obj, String ns, String name, int memberTypeFlags) { return new Node(); }
    public Node createElementGet(Node obj, String ns, Node elem, int memberTypeFlags) { return new Node(); }
    public Node createCall(Node func, Node args) { return new Node(); }
    public Node createNew(Node func, Node args) { return new Node(); }
    public Node createComma(Node left, Node right) { return new Node(); }
    public Node createEmpty() { return new Node(); }
    public void addChildToBack(Node parent, Node child) {}
    public Node createLabel(int lineno) { return new Node(); }
    public Node createLabeledStatement(Node label, Node statement) { return new Node(); }
    public Node createDebuggerStatement(int lineno) { return new Node(); }
    public Node createKeyword(int type, int lineno) { return new Node(); }
    public Node createMemberRefGet(Node obj, String ns, Node elem, int memberTypeFlags) { return new Node(); }
    public Node createNewLocal(Node child) { return new Node(); }
    public Node createUseLocal(Node local) { return new Node(); }
    public Node createXMLRef(Node ref, Node member, int memberTypeFlags) { return new Node(); }
    public Node createDefaultNamespace(Node ns, int lineno) { return new Node(); }
    public void setRequiresActivation() {}
    public void setIsGenerator() {}
    public Node createYield(Node value, int lineno) { return new Node(); }
    public Node createForIn(Node loop, Node init, Node cond, Node body, boolean isForEach) { return new Node(); }
    public void addSwitchCase(Node sw, Node caseExpr, Node block) {}
    public void closeSwitch(Node sw) {}
    public Node getLabelLoop(Node label) { return new Node(); }
    public Node createCondExpr(Node cond, Node ifTrue, Node ifFalse) { return new Node(); }
    public Node createCallOrNew(int type, Node target) { return new Node(); }
    public Node createDotQuery(Node obj, Node body, int lineno) { return new Node(); }
}
