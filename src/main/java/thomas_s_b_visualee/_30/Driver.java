package thomas_s_b_visualee._30;

import thomas_s_b_visualee._30.requirements.source.entity.JavaSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * Driver class for testing jumpOverJavaToken method.
 * Uses reflection to access the protected static method.
 */
public class Driver {
    
    private final Class<?> examinerClass;
    private final Method jumpOverJavaTokenMethod;
    private final Method getSourceCodeScannerMethod;
    
    public Driver(Class<?> examinerClass) throws Exception {
        this.examinerClass = examinerClass;
        this.jumpOverJavaTokenMethod = examinerClass.getDeclaredMethod("jumpOverJavaToken", String.class, Scanner.class);
        this.jumpOverJavaTokenMethod.setAccessible(true);
        this.getSourceCodeScannerMethod = examinerClass.getDeclaredMethod("getSourceCodeScanner", String.class);
        this.getSourceCodeScannerMethod.setAccessible(true);
    }
    
    /**
     * Invokes the jumpOverJavaToken method.
     * 
     * @param token the initial token
     * @param scanner the scanner to read from
     * @return the next non-Java-token
     * @throws Exception if an error occurs during invocation
     */
    public String jumpOverJavaToken(String token, Scanner scanner) throws Exception {
        try {
            return (String) jumpOverJavaTokenMethod.invoke(null, token, scanner);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            throw e;
        }
    }
    
    /**
     * Creates a source code scanner for the given source code.
     * 
     * @param sourceCode the source code to scan
     * @return a Scanner configured for source code
     * @throws Exception if an error occurs during invocation
     */
    public Scanner getSourceCodeScanner(String sourceCode) throws Exception {
        return (Scanner) getSourceCodeScannerMethod.invoke(null, sourceCode);
    }
    
    /**
     * Creates a Driver for the original Examiner class.
     */
    public static Driver createOriginal() throws Exception {
        return new Driver(thomas_s_b_visualee._30.original.Examiner.class);
    }
    
    /**
     * Creates a Driver for the misuse Examiner class.
     */
    public static Driver createMisuse() throws Exception {
        return new Driver(thomas_s_b_visualee._30.misuse.Examiner.class);
    }
    
    /**
     * Creates a Driver for the fixed Examiner class.
     */
    public static Driver createFixed() throws Exception {
        return new Driver(thomas_s_b_visualee._30.fixed.Examiner.class);
    }
}
