package android_rcs_rcsjta._1.mocks;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringUtils {
    // getBytesでの例外処理回避のため、StringではなくCharsetとして定義しておくと安全です
    public static final Charset UTF8 = StandardCharsets.UTF_8;
}