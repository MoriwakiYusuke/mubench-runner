package pawotag._1;

import java.lang.reflect.Method;
import javax.crypto.SecretKey;

/**
 * Reflection-based driver for CryptoUtil testing.
 * Provides access to all public methods of CryptoUtil variants.
 */
public class Driver {

    private final Class<?> targetClass;

    public Driver(String targetClassName) {
        try {
            this.targetClass = Class.forName(targetClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Failed to load class: " + targetClassName, e);
        }
    }

    /**
     * Encrypt a string using the CryptoUtil.encrypt(String) method.
     */
    public String encrypt(String input) {
        return invokeStatic("encrypt", new Class<?>[] { String.class }, input);
    }

    /**
     * Decrypt a string using the CryptoUtil.decrypt(String) method.
     */
    public String decrypt(String input) {
        return invokeStatic("decrypt", new Class<?>[] { String.class }, input);
    }

    /**
     * Set the secret key using CryptoUtil.setSecretKey(SecretKey) method.
     */
    public void setSecretKey(SecretKey key) {
        invokeStatic("setSecretKey", new Class<?>[] { SecretKey.class }, key);
    }

    /**
     * Reset the secret key to null (for test isolation).
     */
    public void resetSecretKey() {
        try {
            java.lang.reflect.Field field = targetClass.getDeclaredField("secretKey");
            field.setAccessible(true);
            field.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to reset secretKey", e);
        }
    }

    /**
     * Get the source code of the encrypt method for pattern checking.
     */
    public String getEncryptMethodSource() throws Exception {
        String filePath = "src/main/java/" + targetClass.getName().replace('.', '/') + ".java";
        return java.nio.file.Files.readString(java.nio.file.Paths.get(filePath));
    }

    /**
     * Check if the encrypt method has proper empty array handling.
     * Returns true if the code checks for empty input before calling doFinal.
     */
    public boolean hasEmptyArrayCheck() throws Exception {
        String source = getEncryptMethodSource();
        // The fix checks if inbytes.length == 0 or inputLength == 0
        return source.contains("inbytes.length == 0") || source.contains("inputLength == 0");
    }

    @SuppressWarnings("unchecked")
    private <T> T invokeStatic(String methodName, Class<?>[] paramTypes, Object... args) {
        try {
            Method method = targetClass.getMethod(methodName, paramTypes);
            return (T) method.invoke(null, args);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            throw new RuntimeException("Failed to invoke " + methodName, e);
        }
    }
}
