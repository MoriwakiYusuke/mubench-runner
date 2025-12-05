package tap_apps._1;

import tap_apps._1.mocks.*;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Driver for tap-apps case 1: Cipher.getInstance() usage.
 * 
 * Uses reflection to dynamically test NSMobileMessenger's Cipher usage.
 * The bug is in Cipher.getInstance("Blowfish") vs Cipher.getInstance("Blowfish/ECB/NoPadding")
 */
public class Driver {
    
    private static final String BASE_PACKAGE = "tap_apps._1";
    
    private final Object instance;
    private final Class<?> targetClass;
    private final String variant;
    
    public Driver(String targetClassName) {
        try {
            this.targetClass = Class.forName(targetClassName);
            Constructor<?> ctor = targetClass.getDeclaredConstructor();
            ctor.setAccessible(true);
            this.instance = ctor.newInstance();
            
            // Extract variant from class name
            String[] parts = targetClassName.split("\\.");
            this.variant = parts.length >= 3 ? parts[2] : "unknown";
            
            // Call doInitialization if exists
            try {
                Method initMethod = targetClass.getDeclaredMethod("doInitialization");
                initMethod.setAccessible(true);
                initMethod.invoke(instance);
            } catch (NoSuchMethodException e) {
                // Ignore if method doesn't exist
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Failed to create driver for: " + targetClassName, e);
        }
    }
    
    /**
     * Get the variant name (original, misuse, fixed).
     */
    public String getVariant() {
        return variant;
    }
    
    // =========================================================================
    // Methods corresponding to NSMobileMessenger public/protected API
    // =========================================================================
    
    /**
     * Get the priority byte value.
     * Corresponds to: byte getPriority()
     */
    public byte getPriority() throws Exception {
        Method method = targetClass.getMethod("getPriority");
        return (Byte) method.invoke(instance);
    }
    
    /**
     * Get the resource bundle name.
     * Corresponds to: String getResourceBundleName()
     */
    public String getResourceBundleName() throws Exception {
        Method method = targetClass.getDeclaredMethod("getResourceBundleName");
        method.setAccessible(true);
        return (String) method.invoke(instance);
    }
    
    /**
     * Pad array to 8-byte boundary.
     * Corresponds to: byte[] padArray8(byte[])
     */
    public byte[] padArray8(byte[] inData) throws Exception {
        Method method = targetClass.getDeclaredMethod("padArray8", byte[].class);
        method.setAccessible(true);
        return (byte[]) method.invoke(instance, inData);
    }
    
    /**
     * Test property validation.
     * Corresponds to: boolean testProperty(String)
     */
    public boolean testProperty(String p) throws Exception {
        Method method = targetClass.getDeclaredMethod("testProperty", String.class);
        method.setAccessible(true);
        return (Boolean) method.invoke(instance, p);
    }
    
    // =========================================================================
    // Cipher-related dynamic test methods
    // =========================================================================
    
    /**
     * Test encryption/decryption using the same Cipher pattern as NSMobileMessenger.
     * This simulates what writeMessageToServer and nodeAsMessageRecord do.
     * 
     * @param key The encryption key (must be at least 8 bytes for Blowfish)
     * @param data The data to encrypt
     * @return The encrypted data
     */
    public byte[] encryptWithCipherPattern(byte[] key, byte[] data) throws Exception {
        // Get the Cipher transformation used by this variant
        String transformation = getCipherTransformationFromSource();
        
        SecretKeySpec sks = new SecretKeySpec(key, "Blowfish");
        Cipher c = Cipher.getInstance(transformation);
        c.init(Cipher.ENCRYPT_MODE, sks);
        
        // Pad data to 8-byte boundary (like padArray8)
        byte[] paddedData = padArray8(data);
        return c.doFinal(paddedData);
    }
    
    /**
     * Test decryption using the same Cipher pattern as NSMobileMessenger.
     * 
     * @param key The decryption key
     * @param encryptedData The data to decrypt
     * @return The decrypted data
     */
    public byte[] decryptWithCipherPattern(byte[] key, byte[] encryptedData) throws Exception {
        String transformation = getCipherTransformationFromSource();
        
        SecretKeySpec sks = new SecretKeySpec(key, "Blowfish");
        Cipher c = Cipher.getInstance(transformation);
        c.init(Cipher.DECRYPT_MODE, sks);
        
        return c.doFinal(encryptedData);
    }
    
    /**
     * Get the Cipher transformation string used by this variant.
     * Reads the source code to find the Cipher.getInstance() call.
     */
    public String getCipherTransformationFromSource() throws Exception {
        String sourceFile = "src/main/java/" + targetClass.getName().replace('.', '/') + ".java";
        String source = java.nio.file.Files.readString(java.nio.file.Paths.get(sourceFile));
        
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(
            "Cipher\\.getInstance\\(\"([^\"]+)\"\\)");
        java.util.regex.Matcher matcher = pattern.matcher(source);
        
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new RuntimeException("No Cipher.getInstance() call found in source");
    }
    
    /**
     * Check if the Cipher transformation explicitly specifies mode and padding.
     * Format should be "Algorithm/Mode/Padding"
     */
    public boolean hasExplicitModeAndPadding() throws Exception {
        String transformation = getCipherTransformationFromSource();
        // Count slashes - should have at least 2 for Algorithm/Mode/Padding
        int slashCount = 0;
        for (char c : transformation.toCharArray()) {
            if (c == '/') slashCount++;
        }
        return slashCount >= 2;
    }
    
    /**
     * Check if the Cipher call is unsafe (just algorithm name without mode/padding).
     */
    public boolean hasUnsafeCipherCall() throws Exception {
        String transformation = getCipherTransformationFromSource();
        return !transformation.contains("/");
    }
    
    /**
     * Perform a round-trip encryption/decryption test.
     * Returns true if the decrypted data matches the original.
     */
    public boolean roundTripTest(byte[] key, byte[] data) throws Exception {
        byte[] encrypted = encryptWithCipherPattern(key, data);
        byte[] decrypted = decryptWithCipherPattern(key, encrypted);
        
        // Compare (accounting for padding)
        if (decrypted.length < data.length) return false;
        for (int i = 0; i < data.length; i++) {
            if (decrypted[i] != data[i]) return false;
        }
        return true;
    }
    
    /**
     * Test that encryption produces different output from input.
     */
    public boolean encryptionProducesDifferentOutput(byte[] key, byte[] data) throws Exception {
        byte[] encrypted = encryptWithCipherPattern(key, data);
        byte[] paddedData = padArray8(data);
        
        // Check that at least some bytes are different
        boolean different = false;
        int minLen = Math.min(encrypted.length, paddedData.length);
        for (int i = 0; i < minLen; i++) {
            if (encrypted[i] != paddedData[i]) {
                different = true;
                break;
            }
        }
        return different;
    }
    
    // =========================================================================
    // Additional methods to cover all public/protected methods in NSMobileMessenger
    // =========================================================================
    
    /**
     * Get the getResourceBundleName method via reflection.
     */
    public java.lang.reflect.Method getResourceBundleNameMethod() throws Exception {
        java.lang.reflect.Method method = targetClass.getDeclaredMethod("getResourceBundleName");
        method.setAccessible(true);
        return method;
    }
    
    /**
     * Get the getPriority method via reflection.
     */
    public java.lang.reflect.Method getPriorityMethod() throws Exception {
        java.lang.reflect.Method method = targetClass.getDeclaredMethod("getPriority");
        method.setAccessible(true);
        return method;
    }
    
    /**
     * Get the startSync method via reflection.
     */
    public java.lang.reflect.Method getStartSyncMethod() throws Exception {
        java.lang.reflect.Method method = targetClass.getDeclaredMethod("startSync", 
            tap_apps._1.mocks.ConduitHandler.class, tap_apps._1.mocks.DLPUserInfo.class);
        method.setAccessible(true);
        return method;
    }
    
    /**
     * Get the constructConfigPanel method via reflection.
     */
    public java.lang.reflect.Method getConstructConfigPanelMethod() throws Exception {
        java.lang.reflect.Method method = targetClass.getDeclaredMethod("constructConfigPanel");
        method.setAccessible(true);
        return method;
    }
    
    /**
     * Get the doInitialization method via reflection.
     */
    public java.lang.reflect.Method getDoInitializationMethod() throws Exception {
        java.lang.reflect.Method method = targetClass.getDeclaredMethod("doInitialization");
        method.setAccessible(true);
        return method;
    }
    
    /**
     * Get the writeMessageToServer method via reflection.
     * This is one of the methods that contains the Cipher usage.
     */
    public java.lang.reflect.Method getWriteMessageToServerMethod() throws Exception {
        java.lang.reflect.Method method = targetClass.getDeclaredMethod("writeMessageToServer",
            String.class, String.class, String.class, 
            tap_apps._1.mocks.MessageRecord.class, HashMap.class, String.class,
            tap_apps._1.mocks.MessageOwnerRecord.class, String.class);
        method.setAccessible(true);
        return method;
    }
    
    /**
     * Get the getMessageHeaders method via reflection.
     */
    public java.lang.reflect.Method getMessageHeadersMethod() throws Exception {
        java.lang.reflect.Method method = targetClass.getDeclaredMethod("getMessageHeaders",
            String.class, String.class, String.class, String.class, String.class);
        method.setAccessible(true);
        return method;
    }
    
    /**
     * Get the testProperty method via reflection.
     */
    public java.lang.reflect.Method getTestPropertyMethod() throws Exception {
        java.lang.reflect.Method method = targetClass.getDeclaredMethod("testProperty", String.class);
        method.setAccessible(true);
        return method;
    }
    
    /**
     * Get the getUserFileName method via reflection.
     */
    public java.lang.reflect.Method getUserFileNameMethod() throws Exception {
        java.lang.reflect.Method method = targetClass.getDeclaredMethod("getUserFileName",
            tap_apps._1.mocks.DLPUserInfo.class);
        method.setAccessible(true);
        return method;
    }
    
    /**
     * Get the getUserIDMap method via reflection.
     */
    public java.lang.reflect.Method getUserIDMapMethod() throws Exception {
        java.lang.reflect.Method method = targetClass.getDeclaredMethod("getUserIDMap",
            tap_apps._1.mocks.DLPUserInfo.class);
        method.setAccessible(true);
        return method;
    }
    
    /**
     * Get the writeUsersIDMap method via reflection.
     */
    public java.lang.reflect.Method getWriteUsersIDMapMethod() throws Exception {
        java.lang.reflect.Method method = targetClass.getDeclaredMethod("writeUsersIDMap",
            Hashtable.class, tap_apps._1.mocks.DLPUserInfo.class);
        method.setAccessible(true);
        return method;
    }
    
    /**
     * Get the getMessage method via reflection.
     */
    public java.lang.reflect.Method getMessageMethod() throws Exception {
        // Need to find the correct parameter types
        java.lang.reflect.Method[] methods = targetClass.getDeclaredMethods();
        for (java.lang.reflect.Method m : methods) {
            if (m.getName().equals("getMessage")) {
                m.setAccessible(true);
                return m;
            }
        }
        throw new NoSuchMethodException("getMessage not found");
    }
    
    /**
     * Get the nodeAsMessageRecord method via reflection.
     * This is one of the methods that contains the Cipher usage.
     */
    public java.lang.reflect.Method getNodeAsMessageRecordMethod() throws Exception {
        // Need to find the correct parameter types
        java.lang.reflect.Method[] methods = targetClass.getDeclaredMethods();
        for (java.lang.reflect.Method m : methods) {
            if (m.getName().equals("nodeAsMessageRecord")) {
                m.setAccessible(true);
                return m;
            }
        }
        throw new NoSuchMethodException("nodeAsMessageRecord not found");
    }
}
