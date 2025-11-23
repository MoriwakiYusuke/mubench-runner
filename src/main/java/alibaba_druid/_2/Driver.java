package alibaba_druid._2;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
// 委譲先のConfigToolsをインポートします（ここではオリジナルのパスを使用）
import alibaba_druid._1.original.ConfigTools;

/**
 * テストコードからの呼び出しを、実験対象のConfigToolsの静的メソッドに委譲するドライバクラスです。
 * ConfigToolsの静的メソッドを直接呼び出すため、このクラス自体はインスタンスフィールドを持ちません。
 */
public class Driver {

    // --- Decrypt methods ---
    public String decrypt(String cipherText) throws Exception {
        return ConfigTools.decrypt(cipherText);
    }

    public String decrypt(String publicKeyText, String cipherText) throws Exception {
        return ConfigTools.decrypt(publicKeyText, cipherText);
    }

    public String decrypt(PublicKey publicKey, String cipherText) throws Exception {
        return ConfigTools.decrypt(publicKey, cipherText);
    }

    // --- Encrypt methods ---
    public String encrypt(String plainText) throws Exception {
        return ConfigTools.encrypt(plainText);
    }

    public String encrypt(String key, String plainText) throws Exception {
        return ConfigTools.encrypt(key, plainText);
    }

    public String encrypt(byte[] keyBytes, String plainText) throws Exception {
        return ConfigTools.encrypt(keyBytes, plainText);
    }

    // --- Key retrieval helpers ---
    public PublicKey getPublicKeyByX509(String x509File) {
        return ConfigTools.getPublicKeyByX509(x509File);
    }

    public PublicKey getPublicKey(String publicKeyText) {
        return ConfigTools.getPublicKey(publicKeyText);
    }

    public PublicKey getPublicKeyByPublicKeyFile(String publicKeyFile) {
        return ConfigTools.getPublicKeyByPublicKeyFile(publicKeyFile);
    }

    // --- Key generation helpers ---
    public byte[][] genKeyPairBytes(int keySize) throws NoSuchAlgorithmException {
        return ConfigTools.genKeyPairBytes(keySize);
    }

    public String[] genKeyPair(int keySize) throws NoSuchAlgorithmException {
        return ConfigTools.genKeyPair(keySize);
    }
}