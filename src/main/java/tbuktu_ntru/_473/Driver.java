package tbuktu_ntru._473;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Driver for tbuktu-ntru case 473.
 * Tests EncryptionParameters.writeTo(OutputStream) for missing flush() call.
 * 
 * Covers all public methods of EncryptionParameters:
 * - Constructors: (int,int,int,int,int,int,int,int,int,boolean,byte[],boolean,boolean,String)
 *                 (int,int,int,int,int,int,int,int,int,int,int,boolean,byte[],boolean,boolean,String)
 *                 (InputStream)
 * - Methods: clone(), getMaxMessageLength(), getOutputLength(), writeTo(OutputStream),
 *            hashCode(), equals(Object), toString()
 */
public class Driver {

    private static final String VARIANT_PACKAGE = "tbuktu_ntru._473";
    
    private final Object instance;
    private final Class<?> clazz;

    /**
     * Creates a Driver with the EES1087EP2 constant from the specified variant.
     */
    public Driver(String variant) throws Exception {
        String className = VARIANT_PACKAGE + "." + variant + ".EncryptionParameters";
        this.clazz = Class.forName(className);
        this.instance = clazz.getField("EES1087EP2").get(null);
    }

    /**
     * Creates a Driver using the InputStream constructor.
     */
    public Driver(String variant, InputStream is) throws Exception {
        String className = VARIANT_PACKAGE + "." + variant + ".EncryptionParameters";
        this.clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor(InputStream.class);
        this.instance = constructor.newInstance(is);
    }

    /**
     * Creates a Driver using the 14-parameter constructor.
     */
    public Driver(String variant, int N, int q, int df, int dm0, int maxM1, int db, int c,
                  int minCallsR, int minCallsMask, boolean hashSeed, byte[] oid,
                  boolean sparse, boolean fastFp, String hashAlg) throws Exception {
        String className = VARIANT_PACKAGE + "." + variant + ".EncryptionParameters";
        this.clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor(
            int.class, int.class, int.class, int.class, int.class, int.class, int.class,
            int.class, int.class, boolean.class, byte[].class, boolean.class, boolean.class, String.class
        );
        this.instance = constructor.newInstance(N, q, df, dm0, maxM1, db, c, minCallsR, minCallsMask,
                                                 hashSeed, oid, sparse, fastFp, hashAlg);
    }

    /**
     * Creates a Driver using the 16-parameter constructor (PRODUCT polynomial type).
     */
    public Driver(String variant, int N, int q, int df1, int df2, int df3, int dm0, int maxM1,
                  int db, int c, int minCallsR, int minCallsMask, boolean hashSeed, byte[] oid,
                  boolean sparse, boolean fastFp, String hashAlg) throws Exception {
        String className = VARIANT_PACKAGE + "." + variant + ".EncryptionParameters";
        this.clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor(
            int.class, int.class, int.class, int.class, int.class, int.class, int.class,
            int.class, int.class, int.class, int.class, boolean.class, byte[].class,
            boolean.class, boolean.class, String.class
        );
        this.instance = constructor.newInstance(N, q, df1, df2, df3, dm0, maxM1, db, c,
                                                 minCallsR, minCallsMask, hashSeed, oid, sparse, fastFp, hashAlg);
    }

    /**
     * Invokes clone() on EncryptionParameters.
     */
    public Object invokeClone() throws Exception {
        Method method = clazz.getMethod("clone");
        return method.invoke(instance);
    }

    /**
     * Invokes getMaxMessageLength() on EncryptionParameters.
     */
    public int getMaxMessageLength() throws Exception {
        Method method = clazz.getMethod("getMaxMessageLength");
        return (int) method.invoke(instance);
    }

    /**
     * Invokes getOutputLength() on EncryptionParameters.
     */
    public int getOutputLength() throws Exception {
        Method method = clazz.getMethod("getOutputLength");
        return (int) method.invoke(instance);
    }

    /**
     * Invokes writeTo(OutputStream) on EncryptionParameters.
     * This is the method under test - it should flush the DataOutputStream.
     */
    public void writeTo(OutputStream os) throws Exception {
        Method method = clazz.getMethod("writeTo", OutputStream.class);
        method.invoke(instance, os);
    }

    /**
     * Invokes hashCode() on EncryptionParameters.
     */
    public int invokeHashCode() throws Exception {
        Method method = clazz.getMethod("hashCode");
        return (int) method.invoke(instance);
    }

    /**
     * Invokes equals(Object) on EncryptionParameters.
     */
    public boolean invokeEquals(Object obj) throws Exception {
        Method method = clazz.getMethod("equals", Object.class);
        return (boolean) method.invoke(instance, obj);
    }

    /**
     * Invokes toString() on EncryptionParameters.
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
     * Invokes writeTo(OutputStream) on EncryptionParameters using reflection.
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
        // Do NOT call bos.flush() here - we want to test if writeTo() calls flush()
        return baos.toByteArray().length;
    }
}
