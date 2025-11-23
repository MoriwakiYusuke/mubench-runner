package apache_gora._56_2.mocks;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Properties;

/**
 * WritableUtils のコンパイル用モック.
 * TestWritableUtils.java が使用するメソッドを定義.
 */
public class WritableUtils {
    
    // TestWritableUtils.java が参照するメソッド
    public static void writeProperties(DataOutput out, Properties props) throws IOException {
        // 実装は不要 (コンパイルが通ればOK)
    }

    public static Properties readProperties(DataInput in) throws IOException {
        return new Properties(); // ダミーのPropertiesを返す
    }
}