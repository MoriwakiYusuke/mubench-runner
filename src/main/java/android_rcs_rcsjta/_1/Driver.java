package android_rcs_rcsjta._1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 実装クラス(Original/Misuse/Fixed)を動的に切り替え可能なドライバ
 */
public class Driver {

    private Class<?> targetClass;

    // コンストラクタで初期クラスを指定
    public Driver(Class<?> initialTargetClass) {
        this.targetClass = initialTargetClass;
    }

    /**
     * ★重要: ここで委譲先を動的に変更します
     */
    public void setTargetClass(Class<?> newTargetClass) {
        this.targetClass = newTargetClass;
    }

    // --- Functional API (リフレクションで実行) ---

    public String getContributionId(String callId) throws Exception {
        // targetClass の static メソッド "getContributionId" を検索して実行
        Method method = targetClass.getMethod("getContributionId", String.class);
        return (String) method.invoke(null, callId);
    }

    // --- Testing/QA API (privateフィールド操作用) ---

    /**
     * 現在のターゲットクラスの secretKey を強制書き換え
     */
    public void setSecretKey(byte[] newKey) throws Exception {
        Field field = targetClass.getDeclaredField("secretKey");
        field.setAccessible(true);
        field.set(null, newKey);
    }

    /**
     * 現在のターゲットクラスの secretKey を取得
     */
    public byte[] getSecretKey() throws Exception {
        Field field = targetClass.getDeclaredField("secretKey");
        field.setAccessible(true);
        return (byte[]) field.get(null);
    }
}