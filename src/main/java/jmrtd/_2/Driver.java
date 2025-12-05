package jmrtd._2;

import jmrtd._2.requirements.CardService;
import jmrtd._2.requirements.SecureMessagingWrapper;
import jmrtd._2.requirements.AuthListener;
import jmrtd._2.requirements.APDUListener;
import jmrtd._2.requirements.Apdu;

import javax.crypto.Cipher;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.PublicKey;

/**
 * Reflection-based driver for PassportAuthService variants.
 * 
 * Bug: Using Cipher.ENCRYPT_MODE instead of Cipher.DECRYPT_MODE in doAA() method.
 * - original: uses Cipher.DECRYPT_MODE (correct)
 * - misuse: uses Cipher.ENCRYPT_MODE (BUG)
 * - fixed: LLM should change ENCRYPT_MODE to DECRYPT_MODE
 * 
 * This driver provides:
 * 1. Dynamic execution via reflection (all public/private methods accessible)
 * 2. Static source code analysis for mode detection
 */
public class Driver {

    private final String targetClassName;
    private final Class<?> targetClass;
    private final Object instance;
    private final String sourceFilePath;

    // Reflected methods (nullable for non-PassportAuthService classes)
    private Method open;
    private Method openWithId;
    private Method getTerminals;
    private Method doBAC;
    private Method addAuthenticationListener;
    private Method removeAuthenticationListener;
    private Method doAA;
    private Method sendAPDU;
    private Method close;
    private Method addAPDUListener;
    private Method removeAPDUListener;
    private Method getWrapper;
    private Method setWrapper;
    private Method notifyBACPerformed;
    private Method notifyAAPerformed;

    // Reflected fields (nullable for non-PassportAuthService classes)
    private Field aaCipherField;
    private Field stateField;

    // Flag to indicate if this is a PassportAuthService class
    private final boolean isPassportAuthService;

    /**
     * Creates a driver for the specified class variant.
     * Supports both PassportAuthService and other classes (for LLM failure cases).
     * 
     * NOTE: PassportAuthService instances are NOT created due to dependency on 
     * ISO9796-2 signature algorithm which is not available in standard JDK.
     * Dynamic tests use independent Cipher tests instead of instance methods.
     * 
     * @param targetClassName fully qualified class name (e.g., "jmrtd._2.misuse.PassportAuthService")
     */
    public Driver(String targetClassName) {
        this.targetClassName = targetClassName;
        this.sourceFilePath = "src/main/java/" + targetClassName.replace('.', '/') + ".java";

        Class<?> loadedClass = null;
        boolean isPassport = false;

        try {
            // Load target class
            loadedClass = Class.forName(targetClassName);

            // Check if this is a PassportAuthService class
            isPassport = targetClassName.contains("PassportAuthService");

            // Note: We do NOT instantiate PassportAuthService because its constructor
            // requires SHA1WithRSA/ISO9796-2 Signature which is not available in standard JDK.
            // Dynamic verification is done via independent Cipher tests in the test class.

            // Set all reflection fields to null - not used for source-based analysis
            this.open = null;
            this.openWithId = null;
            this.getTerminals = null;
            this.doBAC = null;
            this.addAuthenticationListener = null;
            this.removeAuthenticationListener = null;
            this.doAA = null;
            this.sendAPDU = null;
            this.close = null;
            this.addAPDUListener = null;
            this.removeAPDUListener = null;
            this.getWrapper = null;
            this.setWrapper = null;
            this.notifyBACPerformed = null;
            this.notifyAAPerformed = null;
            this.aaCipherField = null;
            this.stateField = null;

        } catch (ClassNotFoundException e) {
            // Class not found - will rely on source analysis only
        }

        this.targetClass = loadedClass;
        this.instance = null;  // No instance created
        this.isPassportAuthService = isPassport;
    }

    /**
     * Returns whether this driver is for a PassportAuthService class.
     */
    public boolean isPassportAuthService() {
        return isPassportAuthService;
    }

    // ========== Mock CardService ==========

    private CardService createMockCardService() {
        return new CardService() {
            @Override
            public void open() {}
            @Override
            public void open(String id) {}
            @Override
            public String[] getTerminals() { return new String[0]; }
            @Override
            public void close() {}
            @Override
            public byte[] sendAPDU(Apdu capdu) { return new byte[0]; }
            @Override
            public void addAPDUListener(APDUListener l) {}
            @Override
            public void removeAPDUListener(APDUListener l) {}
        };
    }

    // ========== Dynamic Execution Methods ==========

    /**
     * Opens a session.
     */
    public void open() {
        try {
            open.invoke(instance);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to invoke open", e);
        }
    }

    /**
     * Opens a session with terminal ID.
     */
    public void open(String id) {
        try {
            openWithId.invoke(instance, id);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to invoke open(id)", e);
        }
    }

    /**
     * Gets available terminals.
     */
    public String[] getTerminals() {
        try {
            return (String[]) getTerminals.invoke(instance);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to invoke getTerminals", e);
        }
    }

    /**
     * Performs Basic Access Control.
     */
    public void doBAC(String docNr, String dateOfBirth, String dateOfExpiry) throws Exception {
        doBAC.invoke(instance, docNr, dateOfBirth, dateOfExpiry);
    }

    /**
     * Adds an authentication listener.
     */
    public void addAuthenticationListener(AuthListener l) {
        try {
            addAuthenticationListener.invoke(instance, l);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to invoke addAuthenticationListener", e);
        }
    }

    /**
     * Removes an authentication listener.
     */
    public void removeAuthenticationListener(AuthListener l) {
        try {
            removeAuthenticationListener.invoke(instance, l);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to invoke removeAuthenticationListener", e);
        }
    }

    /**
     * Performs Active Authentication (the method containing the bug).
     */
    public boolean doAA(PublicKey pubkey) throws Exception {
        return (boolean) doAA.invoke(instance, pubkey);
    }

    /**
     * Sends an APDU.
     */
    public byte[] sendAPDU(Apdu capdu) {
        try {
            return (byte[]) sendAPDU.invoke(instance, capdu);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to invoke sendAPDU", e);
        }
    }

    /**
     * Closes the session.
     */
    public void close() {
        try {
            close.invoke(instance);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to invoke close", e);
        }
    }

    /**
     * Adds an APDU listener.
     */
    public void addAPDUListener(APDUListener l) {
        try {
            addAPDUListener.invoke(instance, l);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to invoke addAPDUListener", e);
        }
    }

    /**
     * Removes an APDU listener.
     */
    public void removeAPDUListener(APDUListener l) {
        try {
            removeAPDUListener.invoke(instance, l);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to invoke removeAPDUListener", e);
        }
    }

    /**
     * Gets the secure messaging wrapper.
     */
    public SecureMessagingWrapper getWrapper() {
        try {
            return (SecureMessagingWrapper) getWrapper.invoke(instance);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to invoke getWrapper", e);
        }
    }

    /**
     * Sets the secure messaging wrapper.
     */
    public void setWrapper(SecureMessagingWrapper wrapper) {
        try {
            setWrapper.invoke(instance, wrapper);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to invoke setWrapper", e);
        }
    }

    /**
     * Notifies BAC performed (protected method).
     */
    public void notifyBACPerformed(SecureMessagingWrapper wrapper, byte[] rndICC, byte[] rndIFD, 
            byte[] kICC, byte[] kIFD, boolean success) throws Exception {
        notifyBACPerformed.invoke(instance, wrapper, rndICC, rndIFD, kICC, kIFD, success);
    }

    /**
     * Notifies AA performed (protected method).
     */
    public void notifyAAPerformed(PublicKey pubkey, byte[] m1, byte[] m2, boolean success) throws Exception {
        notifyAAPerformed.invoke(instance, pubkey, m1, m2, success);
    }

    /**
     * Gets the aaCipher field value.
     */
    public Cipher getAaCipher() throws Exception {
        return (Cipher) aaCipherField.get(instance);
    }

    /**
     * Gets the state field value.
     */
    public int getState() throws Exception {
        return (int) stateField.get(instance);
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
     * Checks if the source file contains the PassportAuthService class.
     */
    public boolean containsPassportAuthService() throws IOException {
        String sourceCode = readSourceCode();
        return sourceCode.contains("class PassportAuthService");
    }

    /**
     * Checks if the doAA method uses DECRYPT_MODE (the correct mode).
     * 
     * @return true if Cipher.DECRYPT_MODE is used, false if ENCRYPT_MODE or not found
     */
    public boolean usesDecryptMode() throws IOException {
        String sourceCode = readSourceCode();
        
        // Find doAA method
        int methodStart = sourceCode.indexOf("doAA(");
        if (methodStart < 0) {
            return false;
        }
        
        int methodEnd = findMethodEnd(sourceCode, methodStart);
        String methodBody = sourceCode.substring(methodStart, methodEnd);
        
        return methodBody.contains("Cipher.DECRYPT_MODE");
    }

    /**
     * Checks if the doAA method uses ENCRYPT_MODE (the buggy mode).
     * 
     * @return true if Cipher.ENCRYPT_MODE is used
     */
    public boolean usesEncryptMode() throws IOException {
        String sourceCode = readSourceCode();
        
        int methodStart = sourceCode.indexOf("doAA(");
        if (methodStart < 0) {
            return false;
        }
        
        int methodEnd = findMethodEnd(sourceCode, methodStart);
        String methodBody = sourceCode.substring(methodStart, methodEnd);
        
        return methodBody.contains("Cipher.ENCRYPT_MODE");
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

    // ========== Dynamic Testing Methods ==========

    /**
     * Gets the Cipher mode constant by reading the source.
     * 
     * @return "DECRYPT_MODE" or "ENCRYPT_MODE" or "UNKNOWN"
     */
    public String getCipherModeFromSource() throws IOException {
        String sourceCode = readSourceCode();
        
        int methodStart = sourceCode.indexOf("doAA(");
        if (methodStart < 0) {
            return "UNKNOWN";
        }
        
        int methodEnd = findMethodEnd(sourceCode, methodStart);
        String methodBody = sourceCode.substring(methodStart, methodEnd);
        
        if (methodBody.contains("Cipher.DECRYPT_MODE")) {
            return "DECRYPT_MODE";
        } else if (methodBody.contains("Cipher.ENCRYPT_MODE")) {
            return "ENCRYPT_MODE";
        }
        return "UNKNOWN";
    }
}
