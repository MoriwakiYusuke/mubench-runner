package adempiere._1;

import adempiere._1.requirements.SecureInterface;
import java.math.BigDecimal;
import java.sql.Timestamp;

// テストコードはこのクラスを通じて SecureInterface を操作する
public class Driver {
    
    private final SecureInterface target;

    // コンストラクタで「操作対象の実体」を受け取る
    public Driver(SecureInterface target) {
        this.target = target;
    }

    // String メソッド (既存)
    public String encrypt(String value) {
        return target.encrypt(value);
    }

    public String decrypt(String value) {
        return target.decrypt(value);
    }

    public String getDigest(String value) {
        return target.getDigest(value);
    }
    
    // 【追加部分】Integer 型のメソッド
    public Integer encrypt(Integer value) {
        return target.encrypt(value);
    }
    
    public Integer decrypt(Integer value) {
        return target.decrypt(value);
    }

    // 【追加部分】BigDecimal 型のメソッド
    public BigDecimal encrypt(BigDecimal value) {
        return target.encrypt(value);
    }
    
    public BigDecimal decrypt(BigDecimal value) {
        return target.decrypt(value);
    }

    // 【追加部分】Timestamp 型のメソッド
    public Timestamp encrypt(Timestamp value) {
        return target.encrypt(value);
    }
    
    public Timestamp decrypt(Timestamp value) {
        return target.decrypt(value);
    }
    
    // 【追加部分】isDigest メソッド
    public boolean isDigest(String value) {
        return target.isDigest(value);
    }

    // 【追加部分】toString メソッド
    public String targetToString() {
        return target.toString();
    }

    // === Static methods (リフレクション経由でアクセス) ===
    
    /**
     * hash - 静的メソッド
     * @param targetClass 対象クラス (original/misuse/fixed)
     * @param key ハッシュ対象の文字列
     * @return ハッシュ値
     */
    public static int hash(Class<?> targetClass, String key) {
        try {
            java.lang.reflect.Method method = targetClass.getMethod("hash", String.class);
            return (int) method.invoke(null, key);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke hash", e);
        }
    }

    /**
     * convertToHexString - バイト配列を16進文字列に変換
     * @param targetClass 対象クラス
     * @param bytes バイト配列
     * @return 16進文字列
     */
    public static String convertToHexString(Class<?> targetClass, byte[] bytes) {
        try {
            java.lang.reflect.Method method = targetClass.getMethod("convertToHexString", byte[].class);
            return (String) method.invoke(null, bytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke convertToHexString", e);
        }
    }

    /**
     * convertHexString - 16進文字列をバイト配列に変換
     * @param targetClass 対象クラス
     * @param hexString 16進文字列
     * @return バイト配列
     */
    public static byte[] convertHexString(Class<?> targetClass, String hexString) {
        try {
            java.lang.reflect.Method method = targetClass.getMethod("convertHexString", String.class);
            return (byte[]) method.invoke(null, hexString);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke convertHexString", e);
        }
    }
}