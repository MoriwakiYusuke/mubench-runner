package tbuktu_ntru._474;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Driver for tbuktu-ntru case 474.
 * Tests SignatureParameters.writeTo(OutputStream) for missing flush() call.
 * 
 * Covers all public methods of SignatureParameters:
 * - Constructors: (int,int,int,int,BasisType,float,float,float,boolean,boolean,KeyGenAlg,String)
 *                 (int,int,int,int,int,int,BasisType,float,float,float,boolean,boolean,KeyGenAlg,String)
 *                 (InputStream)
 * - Methods: getOutputLength(), writeTo(OutputStream), clone(), hashCode(), equals(Object), toString()
 */
public class Driver {

    private static final String VARIANT_PACKAGE = "tbuktu_ntru._474";
    
    private final Object instance;
    private final Class<?> clazz;

    /**
     * Creates a Driver with the APR2011_439 constant from the specified variant.
     */
    public Driver(String variant) throws Exception {
        String className = VARIANT_PACKAGE + "." + variant + ".SignatureParameters";
        this.clazz = Class.forName(className);
        this.instance = clazz.getField("APR2011_439").get(null);
    }

    /**
     * Creates a Driver using the InputStream constructor.
     */
    public Driver(String variant, InputStream is) throws Exception {
        String className = VARIANT_PACKAGE + "." + variant + ".SignatureParameters";
        this.clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor(InputStream.class);
        this.instance = constructor.newInstance(is);
    }

    /**
     * Invokes getOutputLength() on SignatureParameters.
     */
    public int getOutputLength() throws Exception {
        Method method = clazz.getMethod("getOutputLength");
        return (int) method.invoke(instance);
    }

    /**
     * Invokes writeTo(OutputStream) on SignatureParameters.
     * This is the method under test - it should flush the DataOutputStream.
     */
    public void writeTo(OutputStream os) throws Exception {
        Method method = clazz.getMethod("writeTo", OutputStream.class);
        method.invoke(instance, os);
    }

    /**
     * Invokes clone() on SignatureParameters.
     */
    public Object invokeClone() throws Exception {
        Method method = clazz.getMethod("clone");
        return method.invoke(instance);
    }

    /**
     * Invokes hashCode() on SignatureParameters.
     */
    public int invokeHashCode() throws Exception {
        Method method = clazz.getMethod("hashCode");
        return (int) method.invoke(instance);
    }

    /**
     * Invokes equals(Object) on SignatureParameters.
     */
    public boolean invokeEquals(Object obj) throws Exception {
        Method method = clazz.getMethod("equals", Object.class);
        return (boolean) method.invoke(instance, obj);
    }

    /**
     * Invokes toString() on SignatureParameters.
     */
    public String invokeToString() throws Exception {
        Method method = clazz.getMethod("toString");
        return (String) method.invoke(instance);
    }

    /**
     * Gets the underlying instance for comparison purposes.
     */
    public Object getInstance() {
        return instance;
    }

    // ========== Static convenience methods for backward compatibility ==========

    /**
     * Invokes writeTo(OutputStream) on SignatureParameters using reflection.
     * 
     * @param variant "original", "misuse", or "fixed"
     * @return the bytes written to the output stream
     */
    public static byte[] invokeWriteTo(String variant) throws Exception {
        Driver driver = new Driver(variant);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        driver.writeTo(baos);
        return baos.toByteArray();
    }

    /**
     * Invokes writeTo and returns the length of the written data.
     */
    public static int getWrittenLength(String variant) throws Exception {
        return invokeWriteTo(variant).length;
    }

    /**
     * Tests writeTo with a BufferedOutputStream.
     * If flush() is not called, the data will remain in the buffer and not be written.
     * 
     * @return the number of bytes actually written to the underlying stream
     */
    public int writeToBuffered() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos, 8192);
        writeTo(bos);
        return baos.toByteArray().length;
    }
}
