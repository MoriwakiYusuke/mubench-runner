package adempiere;

// テストコードはこのクラスを通じて SecureInterface を操作する
public class SourceDriver {
    
    private final SecureInterface target;

    // コンストラクタで「操作対象の実体」を受け取る
    public SourceDriver(SecureInterface target) {
        this.target = target;
    }

    public String encrypt(String value) {
        return target.encrypt(value);
    }

    public String decrypt(String value) {
        return target.decrypt(value);
    }

    public String getDigest(String value) {
        return target.getDigest(value);
    }
}