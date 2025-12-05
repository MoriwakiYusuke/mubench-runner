package tbuktu_ntru._476;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Driver for tbuktu-ntru Case 476: SignaturePublicKey.getEncoded()
 * 
 * Bug pattern: DataOutputStream.flush() is not called before toByteArray()
 * This means buffered data might not be written correctly.
 * 
 * Covers all public methods of SignaturePublicKey:
 * - Constructors: (byte[]), (InputStream)
 * - Methods: getEncoded(), writeTo(OutputStream), hashCode(), equals(Object)
 */
public class Driver {
    
    private static final String VARIANT_PACKAGE = "tbuktu_ntru._476";
    
    private final Object instance;
    private final Class<?> clazz;
    private final String variant;

    /**
     * Creates a Driver using the byte[] constructor.
     */
    public Driver(String variant, byte[] bytes) throws Exception {
        this.variant = variant;
        String className = VARIANT_PACKAGE + "." + variant + ".SignaturePublicKey";
        this.clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor(byte[].class);
        this.instance = constructor.newInstance(bytes);
    }

    /**
     * Creates a Driver using the InputStream constructor.
     */
    public Driver(String variant, InputStream is) throws Exception {
        this.variant = variant;
        String className = VARIANT_PACKAGE + "." + variant + ".SignaturePublicKey";
        this.clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor(InputStream.class);
        this.instance = constructor.newInstance(is);
    }

    /**
     * Invokes getEncoded() on SignaturePublicKey.
     * This is the method under test - it should flush the DataOutputStream before toByteArray().
     */
    public byte[] getEncoded() throws Exception {
        Method method = clazz.getMethod("getEncoded");
        return (byte[]) method.invoke(instance);
    }

    /**
     * Invokes writeTo(OutputStream) on SignaturePublicKey.
     */
    public void writeTo(OutputStream os) throws Exception {
        Method method = clazz.getMethod("writeTo", OutputStream.class);
        method.invoke(instance, os);
    }

    /**
     * Invokes hashCode() on SignaturePublicKey.
     */
    public int invokeHashCode() throws Exception {
        Method method = clazz.getMethod("hashCode");
        return (int) method.invoke(instance);
    }

    /**
     * Invokes equals(Object) on SignaturePublicKey.
     */
    public boolean invokeEquals(Object obj) throws Exception {
        Method method = clazz.getMethod("equals", Object.class);
        return (boolean) method.invoke(instance, obj);
    }

    /**
     * Gets the underlying instance for comparison purposes.
     */
    public Object getInstance() {
        return instance;
    }

    /**
     * Test helper: Creates test bytes for a minimal SignaturePublicKey.
     * Creates the minimum valid structure for deserialization.
     */
    public static byte[] createMinimalTestBytes(int N, int q) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        
        // Write header
        dos.writeShort(N);
        dos.writeShort(q);
        
        // Write h polynomial as IntegerPolynomial
        int bitsPerCoeff = 32 - Integer.numberOfLeadingZeros(q - 1);
        int totalBits = N * bitsPerCoeff;
        int numBytes = (totalBits + 7) / 8;
        byte[] hBytes = new byte[numBytes];
        dos.write(hBytes);
        
        dos.flush();
        return bos.toByteArray();
    }

    /**
     * Returns the source file path for the current variant.
     */
    public String getSourceFilePath() {
        return "src/main/java/tbuktu_ntru/_476/" + variant + "/SignaturePublicKey.java";
    }

    /**
     * Checks if the getEncoded() method in the source code calls flush() or close().
     * This is a static analysis check for the bug pattern.
     * Either flush() or close() is acceptable as a fix.
     */
    public boolean hasFlushOrCloseInGetEncoded() throws Exception {
        String source = Files.readString(Paths.get(getSourceFilePath()));
        
        // Extract getEncoded method body
        int start = source.indexOf("public byte[] getEncoded()");
        if (start == -1) return false;
        
        // Find the method body by counting braces
        int braceCount = 0;
        int methodStart = source.indexOf("{", start);
        int methodEnd = methodStart;
        for (int i = methodStart; i < source.length(); i++) {
            char c = source.charAt(i);
            if (c == '{') braceCount++;
            else if (c == '}') {
                braceCount--;
                if (braceCount == 0) {
                    methodEnd = i;
                    break;
                }
            }
        }
        
        String methodBody = source.substring(methodStart, methodEnd + 1);
        return methodBody.contains(".flush()") || methodBody.contains(".close()");
    }
}
