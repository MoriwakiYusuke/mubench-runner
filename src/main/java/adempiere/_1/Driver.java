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
}