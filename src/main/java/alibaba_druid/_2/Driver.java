package alibaba_druid._2;

import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/**
 * 実装クラス(Original/Misuse/Fixed)を動的に切り替え可能な汎用ドライバ
 */
public class Driver {

    private Class<?> targetClass;

    // コンストラクタで初期ターゲットを指定
    public Driver(Class<?> initialTargetClass) {
        this.targetClass = initialTargetClass;
    }

    /**
     * 委譲先クラスを動的に変更するメソッド
     */
    public void setTargetClass(Class<?> newTargetClass) {
        this.targetClass = newTargetClass;
    }

    // --- Helper for static method call ---
    // リフレクション記述の重複を避けるためのヘルパー
    @SuppressWarnings("unchecked")
    private <T> T callStatic(String methodName, Class<?>[] paramTypes, Object... args) throws Exception {
        Method method = targetClass.getMethod(methodName, paramTypes);
        return (T) method.invoke(null, args);
    }

    // --- Decrypt methods ---

    public String decrypt(String cipherText) throws Exception {
        return callStatic("decrypt", 
            new Class<?>[]{String.class}, 
            cipherText);
    }

    public String decrypt(String publicKeyText, String cipherText) throws Exception {
        return callStatic("decrypt", 
            new Class<?>[]{String.class, String.class}, 
            publicKeyText, cipherText);
    }

    public String decrypt(PublicKey publicKey, String cipherText) throws Exception {
        return callStatic("decrypt", 
            new Class<?>[]{PublicKey.class, String.class}, 
            publicKey, cipherText);
    }

    // --- Encrypt methods ---

    public String encrypt(String plainText) throws Exception {
        return callStatic("encrypt", 
            new Class<?>[]{String.class}, 
            plainText);
    }

    public String encrypt(String key, String plainText) throws Exception {
        return callStatic("encrypt", 
            new Class<?>[]{String.class, String.class}, 
            key, plainText);
    }

    public String encrypt(byte[] keyBytes, String plainText) throws Exception {
        return callStatic("encrypt", 
            new Class<?>[]{byte[].class, String.class}, 
            keyBytes, plainText);
    }

    // --- Key retrieval helpers ---

    public PublicKey getPublicKeyByX509(String x509File) {
        try {
            return callStatic("getPublicKeyByX509", 
                new Class<?>[]{String.class}, 
                x509File);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PublicKey getPublicKey(String publicKeyText) {
        try {
            return callStatic("getPublicKey", 
                new Class<?>[]{String.class}, 
                publicKeyText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PublicKey getPublicKeyByPublicKeyFile(String publicKeyFile) {
        try {
            return callStatic("getPublicKeyByPublicKeyFile", 
                new Class<?>[]{String.class}, 
                publicKeyFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // --- Key generation helpers ---

    public byte[][] genKeyPairBytes(int keySize) throws NoSuchAlgorithmException {
        try {
            return callStatic("genKeyPairBytes", 
                new Class<?>[]{int.class}, 
                keySize);
        } catch (Exception e) {
            if (e.getCause() instanceof NoSuchAlgorithmException) {
                throw (NoSuchAlgorithmException) e.getCause();
            }
            throw new RuntimeException(e);
        }
    }

    public String[] genKeyPair(int keySize) throws NoSuchAlgorithmException {
        try {
            return callStatic("genKeyPair", 
                new Class<?>[]{int.class}, 
                keySize);
        } catch (Exception e) {
            if (e.getCause() instanceof NoSuchAlgorithmException) {
                throw (NoSuchAlgorithmException) e.getCause();
            }
            throw new RuntimeException(e);
        }
    }

    // --- Main method ---

    /**
     * Invoke main method of ConfigTools.
     * This generates a key pair and prints it to stdout.
     */
    public void main(String[] args) throws Exception {
        callStatic("main", 
            new Class<?>[]{String[].class}, 
            (Object) args);
    }
}