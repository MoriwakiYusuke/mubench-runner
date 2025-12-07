package thomas_s_b_visualee._29;

import thomas_s_b_visualee._29.requirements.source.entity.JavaSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Driver class for testing findAndSetPackage method.
 * Uses reflection to access the protected static method.
 */
public class Driver {
    
    private final Class<?> examinerClass;
    private final Method findAndSetPackageMethod;
    
    public Driver(Class<?> examinerClass) throws Exception {
        this.examinerClass = examinerClass;
        this.findAndSetPackageMethod = examinerClass.getDeclaredMethod("findAndSetPackage", JavaSource.class);
        this.findAndSetPackageMethod.setAccessible(true);
    }
    
    /**
     * Invokes the findAndSetPackage method on the given JavaSource.
     * The method scans the source code for a package declaration and sets it on the JavaSource.
     * 
     * @param javaSource the JavaSource to process
     * @throws Exception if an error occurs during invocation
     */
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
    
    /**
     * Creates a Driver for the original Examiner class.
     */
    public static Driver createOriginal() throws Exception {
        return new Driver(thomas_s_b_visualee._29.original.Examiner.class);
    }
    
    /**
     * Creates a Driver for the misuse Examiner class.
     */
    public static Driver createMisuse() throws Exception {
        return new Driver(thomas_s_b_visualee._29.misuse.Examiner.class);
    }
    
    /**
     * Creates a Driver for the fixed Examiner class.
     */
    public static Driver createFixed() throws Exception {
        return new Driver(thomas_s_b_visualee._29.fixed.Examiner.class);
    }
}
