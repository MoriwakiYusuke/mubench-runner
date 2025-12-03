package rhino._1.mocks;

import java.util.ArrayList;

public class ObjArray extends ArrayList<Object> {
    public Object peek() { return isEmpty() ? null : get(size() - 1); }
    public Object pop() { return isEmpty() ? null : remove(size() - 1); }
    public void push(Object obj) { add(obj); }
}
