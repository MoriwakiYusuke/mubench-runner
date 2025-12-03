package rhino._1.mocks;

public class Node {
    public static final int FUNCTION_PROP = 0;
    public static final int LOCAL_PROP = 1;
    public static final int LOCAL_BLOCK_PROP = 2;
    public static final int DESCENDANTS_FLAG = 1;
    public static final int ATTRIBUTE_FLAG = 2;
    
    public void addChildToBack(Node child) {}
    public void addChildToFront(Node child) {}
    public Node getFirstChild() { return null; }
    public Node getLastChild() { return null; }
    public Node getNext() { return null; }
    public int getType() { return 0; }
    public void setType(int type) {}
    public int getLineno() { return 0; }
    public void setLineno(int lineno) {}
    public Object getProp(int prop) { return null; }
    public void putProp(int prop, Object value) {}
    public void putIntProp(int prop, int value) {}
    public int getIntProp(int prop, int def) { return def; }
    public void removeProp(int prop) {}
    public String getString() { return ""; }
    public double getDouble() { return 0; }
    public void setDouble(double d) {}
    public void removeChild(Node child) {}
    public void replaceChild(Node oldChild, Node newChild) {}
    public void replaceChildAfter(Node prev, Node node) {}
}
