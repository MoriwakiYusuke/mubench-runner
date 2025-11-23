package androiduil._1;

import androiduil._1.mocks.Context;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * StorageUtils (Original/Misuse/Fixed) の静的メソッドを
 * リフレクション経由で呼び出すための汎用ドライバ。
 */
public class Driver {

    private final Class<?> targetClass;

    /**
     * @param targetClass テスト対象の StorageUtils クラス (例: androiduil._1.original.StorageUtils.class)
     */
    public Driver(Class<?> targetClass) {
        this.targetClass = targetClass;
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
            // テストで期待される例外を捕捉しやすくするため、アンラップしてスロー
            if (e.getTargetException() instanceof Exception) {
                throw (Exception) e.getTargetException();
            }
            throw e;
        }
    }

    // --- Public API Mapping ---

    public File getCacheDirectory(Context context) throws Exception {
        return callStatic("getCacheDirectory",
                new Class<?>[]{Context.class},
                context);
    }

    public File getCacheDirectory(Context context, boolean preferExternal) throws Exception {
        return callStatic("getCacheDirectory",
                new Class<?>[]{Context.class, boolean.class}, // boolean.class (プリミティブ) に注意
                context, preferExternal);
    }

    public File getIndividualCacheDirectory(Context context) throws Exception {
        return callStatic("getIndividualCacheDirectory",
                new Class<?>[]{Context.class},
                context);
    }

    public File getOwnCacheDirectory(Context context, String cacheDir) throws Exception {
        return callStatic("getOwnCacheDirectory",
                new Class<?>[]{Context.class, String.class},
                context, cacheDir);
    }
}