package adempiere._1;

// 元のコード(Secure.java)が要求している仕様をここに定義
public interface SecureInterface {
    // Secure.java で使われている定数
    public static final String CLEARVALUE_START = "";
    public static final String CLEARVALUE_END = "";
    public static final String ENCRYPTEDVALUE_START = "Encrypted:";
    public static final String ENCRYPTEDVALUE_END = "";

    // メソッド
    String encrypt(String value);
    String decrypt(String value);
    String getDigest(String value);
}