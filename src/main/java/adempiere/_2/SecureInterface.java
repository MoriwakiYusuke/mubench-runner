package adempiere._2;

import java.math.BigDecimal;
import java.sql.Timestamp;

// 元のコード(Secure.java)が要求している仕様をここに定義
public interface SecureInterface {
    
    // Secure.java で使われている定数
    public static final String CLEARVALUE_START = "";
    public static final String CLEARVALUE_END = "";
    public static final String ENCRYPTEDVALUE_START = "Encrypted:";
    public static final String ENCRYPTEDVALUE_END = "";

    // String メソッド (既存)
    String encrypt(String value);
    String decrypt(String value);
    String getDigest(String value);
    
    // 【拡張部分】Integer 型のメソッドを追加
    Integer encrypt(Integer value);
    Integer decrypt(Integer value);
    
    // 【拡張部分】BigDecimal 型のメソッドを追加
    BigDecimal encrypt(BigDecimal value);
    BigDecimal decrypt(BigDecimal value);
    
    // 【拡張部分】Timestamp 型のメソッドを追加
    Timestamp encrypt(Timestamp value);
    Timestamp decrypt(Timestamp value);
    
    // Digest のチェックメソッド (Secure.java に存在するため追加)
    boolean isDigest (String value);
}