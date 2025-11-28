package jmrtd._1;

import jmrtd._1.requirements.Util;

import javax.crypto.SecretKey;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.Security;

/**
 * Reflection-based driver for SecureMessagingWrapper variants.
 * 
 * Bug: DataOutputStream.close() not called in readDO8E() method.
 * - original: has dataOut.close() after mac.doFinal() (correct)
 * - misuse: missing dataOut.close() (BUG - resource leak)
 * - fixed: LLM should add dataOut.close()
 * 
 * This driver provides:
 * 1. Dynamic execution via reflection (all public/private methods accessible)
 * 2. Static source code analysis for close() detection
 */
public class Driver {

    static {
        // Register BouncyCastle provider for ISO9797Alg3Mac
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    private final String targetClassName;
    private final Class<?> targetClass;
    private final Object instance;
    private final String sourceFilePath;

    // Reflected methods
    private final Method getSendSequenceCounter;
    private final Method wrap;
    private final Method unwrap;
    private final Method wrapCommandAPDU;
    private final Method unwrapResponseAPDU;
    private final Method readDO87;
    private final Method readDO99;
    private final Method readDO8E;

    /**
     * Creates a driver for the specified SecureMessagingWrapper variant.
     * 
     * @param targetClassName fully qualified class name (e.g., "jmrtd._1.misuse.SecureMessagingWrapper")
     */
    public Driver(String targetClassName) {
        this.targetClassName = targetClassName;
        this.sourceFilePath = "src/main/java/" + targetClassName.replace('.', '/') + ".java";

        try {
            // Load target class
            this.targetClass = Class.forName(targetClassName);

            // Create test keys
            byte[] keySeed = new byte[16];
            for (int i = 0; i < 16; i++) {
                keySeed[i] = (byte) i;
            }
            SecretKey ksEnc = Util.deriveKey(keySeed, Util.ENC_MODE);
            SecretKey ksMac = Util.deriveKey(keySeed, Util.MAC_MODE);

            // Find and invoke constructor
            Constructor<?> constructor = targetClass.getConstructor(SecretKey.class, SecretKey.class, long.class);
            this.instance = constructor.newInstance(ksEnc, ksMac, 0L);

            // Get public methods
            this.getSendSequenceCounter = targetClass.getMethod("getSendSequenceCounter");
            this.wrap = targetClass.getMethod("wrap", byte[].class);
            this.unwrap = targetClass.getMethod("unwrap", byte[].class, int.class);

            // Get private methods via reflection
            this.wrapCommandAPDU = targetClass.getDeclaredMethod("wrapCommandAPDU", byte[].class, int.class);
            this.wrapCommandAPDU.setAccessible(true);

            this.unwrapResponseAPDU = targetClass.getDeclaredMethod("unwrapResponseAPDU", byte[].class, int.class);
            this.unwrapResponseAPDU.setAccessible(true);

            this.readDO87 = targetClass.getDeclaredMethod("readDO87", DataInputStream.class);
            this.readDO87.setAccessible(true);

            this.readDO99 = targetClass.getDeclaredMethod("readDO99", DataInputStream.class);
            this.readDO99.setAccessible(true);

            this.readDO8E = targetClass.getDeclaredMethod("readDO8E", DataInputStream.class, byte[].class);
            this.readDO8E.setAccessible(true);

        } catch (ReflectiveOperationException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to initialize driver for " + targetClassName, e);
        }
    }

    // ========== Dynamic Execution Methods ==========

    /**
     * Gets the current value of the send sequence counter.
     */
    public long getSendSequenceCounter() {
        try {
            return (long) getSendSequenceCounter.invoke(instance);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to invoke getSendSequenceCounter", e);
        }
    }

    /**
     * Wraps a command APDU.
     */
    public byte[] wrap(byte[] capdu) {
        try {
            return (byte[]) wrap.invoke(instance, capdu);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to invoke wrap", e);
        }
    }

    /**
     * Unwraps a response APDU.
     */
    public byte[] unwrap(byte[] rapdu, int len) {
        try {
            return (byte[]) unwrap.invoke(instance, rapdu, len);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to invoke unwrap", e);
        }
    }

    /**
     * Invokes private wrapCommandAPDU method.
     */
    public byte[] wrapCommandAPDU(byte[] capdu, int len) throws Exception {
        return (byte[]) wrapCommandAPDU.invoke(instance, capdu, len);
    }

    /**
     * Invokes private unwrapResponseAPDU method.
     */
    public byte[] unwrapResponseAPDU(byte[] rapdu, int len) throws Exception {
        return (byte[]) unwrapResponseAPDU.invoke(instance, rapdu, len);
    }

    /**
     * Invokes private readDO87 method.
     */
    public byte[] readDO87(DataInputStream in) throws Exception {
        return (byte[]) readDO87.invoke(instance, in);
    }

    /**
     * Invokes private readDO99 method.
     */
    public short readDO99(DataInputStream in) throws Exception {
        return (short) readDO99.invoke(instance, in);
    }

    /**
     * Invokes private readDO8E method (the method containing the bug).
     */
    public void readDO8E(DataInputStream in, byte[] rapdu) throws Exception {
        readDO8E.invoke(instance, in, rapdu);
    }

    /**
     * Creates a new instance with custom keys.
     */
    public Object createInstance(SecretKey ksEnc, SecretKey ksMac, long ssc) throws Exception {
        Constructor<?> constructor = targetClass.getConstructor(SecretKey.class, SecretKey.class, long.class);
        return constructor.newInstance(ksEnc, ksMac, ssc);
    }

    /**
     * Gets the underlying instance.
     */
    public Object getInstance() {
        return instance;
    }

    /**
     * Gets the target class.
     */
    public Class<?> getTargetClass() {
        return targetClass;
    }

    // ========== Static Analysis Methods ==========

    /**
     * Returns the source file path for this variant.
     */
    public String getSourceFilePath() {
        return sourceFilePath;
    }

    /**
     * Returns the target class name.
     */
    public String getTargetClassName() {
        return targetClassName;
    }

    /**
     * Reads the source code of the target class.
     */
    public String readSourceCode() throws IOException {
        Path path = Paths.get(sourceFilePath);
        if (!Files.exists(path)) {
            throw new IOException("Source file not found: " + sourceFilePath);
        }
        return Files.readString(path);
    }

    /**
     * Checks if the readDO8E method properly closes the DataOutputStream.
     * 
     * The correct implementation should have dataOut.close() after processing.
     * 
     * @return true if dataOut.close() is properly called, false otherwise
     */
    public boolean hasDataOutputStreamClose() throws IOException {
        String sourceCode = readSourceCode();
        
        // Find readDO8E method
        int methodStart = sourceCode.indexOf("private void readDO8E(");
        if (methodStart < 0) {
            throw new IllegalStateException("readDO8E method not found in source");
        }
        
        // Find the end of the method
        int methodEnd = findMethodEnd(sourceCode, methodStart);
        String methodBody = sourceCode.substring(methodStart, methodEnd);
        
        // Check for dataOut.close() call
        return methodBody.contains("dataOut.close()");
    }

    /**
     * Checks if the readDO8E method has a try-finally block for resource management.
     */
    public boolean hasTryFinallyBlock() throws IOException {
        String sourceCode = readSourceCode();
        
        int methodStart = sourceCode.indexOf("private void readDO8E(");
        if (methodStart < 0) {
            throw new IllegalStateException("readDO8E method not found in source");
        }
        
        int methodEnd = findMethodEnd(sourceCode, methodStart);
        String methodBody = sourceCode.substring(methodStart, methodEnd);
        
        // Check for try-finally pattern
        return methodBody.contains("try {") && methodBody.contains("} finally {");
    }

    /**
     * Finds the approximate end of a method by counting braces.
     */
    private int findMethodEnd(String sourceCode, int methodStart) {
        int braceCount = 0;
        boolean foundFirstBrace = false;
        
        for (int i = methodStart; i < sourceCode.length(); i++) {
            char c = sourceCode.charAt(i);
            if (c == '{') {
                braceCount++;
                foundFirstBrace = true;
            } else if (c == '}') {
                braceCount--;
                if (foundFirstBrace && braceCount == 0) {
                    return i + 1;
                }
            }
        }
        return sourceCode.length();
    }
}
