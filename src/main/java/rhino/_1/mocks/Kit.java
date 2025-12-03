package rhino._1.mocks;

public class Kit {
    public static RuntimeException codeBug() { 
        return new RuntimeException("codeBug"); 
    }
    public static RuntimeException codeBug(String msg) { 
        return new RuntimeException("codeBug: " + msg); 
    }
}
