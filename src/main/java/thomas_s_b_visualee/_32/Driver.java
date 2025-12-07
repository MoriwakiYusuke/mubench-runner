package thomas_s_b_visualee._32;

import thomas_s_b_visualee._32.requirements.source.entity.JavaSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * Driver class for testing scanAfterClosedParenthesis method.
 * Uses reflection to access the protected static method.
 */
public class Driver {
    
    private final Class<?> examinerClass;
    private final Method scanAfterClosedParenthesisMethod;
    private final Method getSourceCodeScannerMethod;
    private final Method examineMethod;
    private final Object examinerInstance;
    
    public Driver(Class<?> examinerClass) throws Exception {
        this.examinerClass = examinerClass;
        this.scanAfterClosedParenthesisMethod = examinerClass.getDeclaredMethod("scanAfterClosedParenthesis", String.class, Scanner.class);
        this.scanAfterClosedParenthesisMethod.setAccessible(true);
        this.getSourceCodeScannerMethod = examinerClass.getDeclaredMethod("getSourceCodeScanner", String.class);
        this.getSourceCodeScannerMethod.setAccessible(true);
        this.examineMethod = examinerClass.getMethod("examine", JavaSource.class);
        this.examinerInstance = examinerClass.getDeclaredConstructor().newInstance();
    }
    
    /**
     * Invokes the scanAfterClosedParenthesis method.
     * This method scans past the closing parenthesis matching the opening one(s) in the token.
     * 
     * @param currentToken the token containing opening parenthesis
     * @param scanner the scanner to read from
     * @return the token after the matching closing parenthesis
     * @throws Exception if an error occurs during invocation
     */
    public String scanAfterClosedParenthesis(String currentToken, Scanner scanner) throws Exception {
        try {
            return (String) scanAfterClosedParenthesisMethod.invoke(null, currentToken, scanner);
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
     * Invokes the examine method on the examiner instance.
     * 
     * @param javaSource the JavaSource to examine
     * @throws Exception if an error occurs during invocation
     */
    public void examine(JavaSource javaSource) throws Exception {
        try {
            examineMethod.invoke(examinerInstance, javaSource);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            throw e;
        }
    }
    
    /**
     * Creates a Driver for the original Examiner class.
     */
    public static Driver createOriginal() throws Exception {
        return new Driver(thomas_s_b_visualee._32.original.Examiner.class);
    }
    
    /**
     * Creates a Driver for the misuse Examiner class.
     */
    public static Driver createMisuse() throws Exception {
        return new Driver(thomas_s_b_visualee._32.misuse.Examiner.class);
    }
    
    /**
     * Creates a Driver for the fixed Examiner class.
     */
    public static Driver createFixed() throws Exception {
        return new Driver(thomas_s_b_visualee._32.fixed.Examiner.class);
    }
}
