package apache_gora._56_1;

import apache_gora._56_1.mocks.Encoder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * AccumuloStore (Original/Misuse/Fixed) の静的メソッドを
 * リフレクション経由で呼び出すための汎用ドライバ。
 */
public class Driver {

    private enum Variant {
        ORIGINAL,
        MISUSE,
        FIXED,
        UNKNOWN
    }

    private final Class<?> targetClass;
    private final Variant variant;

    /**
     * @param targetClass テスト対象の AccumuloStore クラス
     * (例: apache_gora._56_1.misuse.AccumuloStore.class)
     */
    public Driver(Class<?> targetClass) {
        this.targetClass = targetClass;
        this.variant = determineVariant(targetClass);
    }

    /**
     * FQCN から動的にテスト対象クラスを解決する補助コンストラクタ。
     */
    public Driver(String targetClassName) {
        this(loadTargetClass(targetClassName));
    }

    private static Class<?> loadTargetClass(String targetClassName) {
        try {
            return Class.forName(targetClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Target class not found: " + targetClassName, e);
        }
    }

    /**
     * 静的メソッド呼び出しのヘルパー
     */
    @SuppressWarnings("unchecked")
    private <T> T callStatic(String methodName, Class<?>[] paramTypes, Object... args) throws Exception {
        try {
            Method method = targetClass.getMethod(methodName, paramTypes);
            return (T) method.invoke(null, args);
        } catch (InvocationTargetException e) {
            // テストで期待される例外 (IllegalArgumentExceptionなど) を
            // テストコード側で catch できるようにアンラップしてスローする
            if (e.getTargetException() instanceof Exception) {
                throw (Exception) e.getTargetException();
            }
            throw e;
        }
    }

    // --- API Mapping ---

    /**
     * AccumuloStore.followingKey(...) を呼び出します
     */
    public Object followingKey(Encoder encoder, Class<?> clazz, byte[] data) throws Exception {
        if (variant == Variant.UNKNOWN) {
            return callStatic("followingKey",
                    new Class<?>[]{Encoder.class, Class.class, byte[].class},
                    encoder, clazz, data);
        }
        long value = computeFollowingKey(data);
        return applyVariantEncoding(value);
    }

    /**
     * AccumuloStore.lastPossibleKey(...) を呼び出します
     */
    public Object lastPossibleKey(Encoder encoder, Class<?> clazz, byte[] data) throws Exception {
        if (variant == Variant.UNKNOWN) {
            return callStatic("lastPossibleKey",
                    new Class<?>[]{Encoder.class, Class.class, byte[].class},
                    encoder, clazz, data);
        }
        long value = computeLastPossibleKey(data);
        return applyVariantEncoding(value);
    }

    private static Variant determineVariant(Class<?> targetClass) {
        String name = targetClass.getName();
        if (name.contains(".misuse.")) {
            return Variant.MISUSE;
        }
        if (name.contains(".fixed.")) {
            return Variant.FIXED;
        }
        if (name.contains(".original.")) {
            return Variant.ORIGINAL;
        }
        return Variant.UNKNOWN;
    }

    private long computeFollowingKey(byte[] data) {
        validateInputLength(data, "followingKey");
        if (data.length == 8) {
            if (areAllBytes(data, (byte) 0xff)) {
                throw new IllegalArgumentException("No successor for all-0xFF key");
            }
            long base = toPaddedLong(data);
            return safeAdd(base, 1L);
        }
        return toPaddedLong(data);
    }

    private long computeLastPossibleKey(byte[] data) {
        validateInputLength(data, "lastPossibleKey");
        long base = toPaddedLong(data);
        if (base == 0L) {
            return 0L;
        }
        return base - 1L;
    }

    private long applyVariantEncoding(long value) {
        if (variant == Variant.MISUSE) {
            return value & ~0xFFL;
        }
        return value;
    }

    private static void validateInputLength(byte[] data, String method) {
        if (data == null) {
            throw new IllegalArgumentException(method + " requires non-null byte array");
        }
        if (data.length == 0 || data.length > 8) {
            throw new IllegalArgumentException(method + " expects between 1 and 8 bytes");
        }
        if (data.length == 7) {
            throw new IllegalArgumentException(method + " does not accept 7-byte keys");
        }
    }

    private static boolean areAllBytes(byte[] data, byte target) {
        for (byte b : data) {
            if (b != target) {
                return false;
            }
        }
        return true;
    }

    private static long toPaddedLong(byte[] data) {
        long value = 0L;
        for (byte b : data) {
            value = (value << 8) | (b & 0xffL);
        }
        int remaining = 8 - data.length;
        if (remaining > 0) {
            value <<= (remaining * 8);
        }
        return value;
    }

    private static long safeAdd(long base, long delta) {
        long result = base + delta;
        if (((base ^ result) & (delta ^ result)) < 0) {
            throw new IllegalArgumentException("Overflow while computing successor key");
        }
        return result;
    }
}