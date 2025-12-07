package thomas_s_b_visualee._30;

import thomas_s_b_visualee._30.requirements.source.entity.JavaSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * Driver class for testing Examiner methods.
 * Uses reflection to access the protected static methods.
 */
public class Driver {
    
    private final Class<?> examinerClass;
    private final Method findAndSetPackageMethod;
    private final Method getSourceCodeScannerMethod;
    private final Method getClassBodyMethod;
    private final Method countCharMethod;
    private final Method scanAfterQuoteMethod;
    private final Method scanAfterClosedParenthesisMethod;
    private final Method isAJavaTokenMethod;
    private final Method jumpOverJavaTokenMethod;
    private final Method extractClassInstanceOrEventMethod;
    
    public Driver(Class<?> examinerClass) throws Exception {
        this.examinerClass = examinerClass;
        
        this.findAndSetPackageMethod = examinerClass.getDeclaredMethod("findAndSetPackage", JavaSource.class);
        this.findAndSetPackageMethod.setAccessible(true);
        
        this.getSourceCodeScannerMethod = examinerClass.getDeclaredMethod("getSourceCodeScanner", String.class);
        this.getSourceCodeScannerMethod.setAccessible(true);
        
        this.getClassBodyMethod = examinerClass.getDeclaredMethod("getClassBody", String.class);
        this.getClassBodyMethod.setAccessible(true);
        
        this.countCharMethod = examinerClass.getDeclaredMethod("countChar", String.class, char.class);
        this.countCharMethod.setAccessible(true);
        
        this.scanAfterQuoteMethod = examinerClass.getDeclaredMethod("scanAfterQuote", String.class, Scanner.class);
        this.scanAfterQuoteMethod.setAccessible(true);
        
        this.scanAfterClosedParenthesisMethod = examinerClass.getDeclaredMethod("scanAfterClosedParenthesis", String.class, Scanner.class);
        this.scanAfterClosedParenthesisMethod.setAccessible(true);
        
        this.isAJavaTokenMethod = examinerClass.getDeclaredMethod("isAJavaToken", String.class);
        this.isAJavaTokenMethod.setAccessible(true);
        
        this.jumpOverJavaTokenMethod = examinerClass.getDeclaredMethod("jumpOverJavaToken", String.class, Scanner.class);
        this.jumpOverJavaTokenMethod.setAccessible(true);
        
        this.extractClassInstanceOrEventMethod = examinerClass.getDeclaredMethod("extractClassInstanceOrEvent", String.class);
        this.extractClassInstanceOrEventMethod.setAccessible(true);
    }
    
    public void findAndSetPackage(JavaSource javaSource) throws Exception {
        try {
            findAndSetPackageMethod.invoke(null, javaSource);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            throw e;
        }
    }
    
    public Scanner getSourceCodeScanner(String sourceCode) throws Exception {
        return (Scanner) getSourceCodeScannerMethod.invoke(null, sourceCode);
    }
    
    public String getClassBody(String sourceCode) throws Exception {
        return (String) getClassBodyMethod.invoke(null, sourceCode);
    }
    
    public int countChar(String string, char char2Find) throws Exception {
        return (int) countCharMethod.invoke(null, string, char2Find);
    }
    
    public String scanAfterQuote(String currentToken, Scanner scanner) throws Exception {
        try {
            return (String) scanAfterQuoteMethod.invoke(null, currentToken, scanner);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            throw e;
        }
    }
    
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
    
    public boolean isAJavaToken(String token) throws Exception {
        return (boolean) isAJavaTokenMethod.invoke(null, token);
    }
    
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
    
    public String extractClassInstanceOrEvent(String token) throws Exception {
        return (String) extractClassInstanceOrEventMethod.invoke(null, token);
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
