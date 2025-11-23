package androiduil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// モッククラスのインポート
import androiduil._1.mocks.Context;
import androiduil._1.mocks.Environment;

public class AndroiduilTest_1 {

    // =========================================================
    //  Universal Driver (Inner Class)
    // =========================================================
    static class Driver {
        private final Class<?> targetClass;

        public Driver(Class<?> targetClass) {
            this.targetClass = targetClass;
        }

        @SuppressWarnings("unchecked")
        private <T> T callStatic(String methodName, Class<?>[] paramTypes, Object... args) throws Exception {
            try {
                Method method = targetClass.getMethod(methodName, paramTypes);
                return (T) method.invoke(null, args);
            } catch (InvocationTargetException e) {
                if (e.getTargetException() instanceof Exception) {
                    throw (Exception) e.getTargetException();
                }
                throw e;
            }
        }

        public File getCacheDirectory(Context context) throws Exception {
            return callStatic("getCacheDirectory", new Class<?>[]{Context.class}, context);
        }
    }

    // =========================================================
    //  Test Logic
    // =========================================================
    abstract static class CommonLogic {

        abstract Driver getTargetDriver();

        @BeforeEach
        void setUp() {
            // テストごとにモックの状態をリセット (SDカードあり)
            Environment.reset();
        }

        @Test
        @DisplayName("Normal: Returns external cache dir when SD card is mounted")
        void testGetCacheDir_Mounted() throws Exception {
            Driver driver = getTargetDriver();
            Context context = new Context();

            // SDカード: あり
            Environment.setExternalStorageState(Environment.MEDIA_MOUNTED);
            
            File cacheDir = driver.getCacheDirectory(context);
            
            assertNotNull(cacheDir);
            // 外部ストレージのパスが含まれていることを確認
            assertTrue(cacheDir.getAbsolutePath().contains("android-stub-external"),
                "Should return external path when mounted. Got: " + cacheDir.getAbsolutePath());
        }

        @Test
        @DisplayName("Reproduction: Returns INTERNAL cache dir when SD card is REMOVED")
        void testGetCacheDir_Removed() throws Exception {
            Driver driver = getTargetDriver();
            Context context = new Context();

            // SDカード: なし (Removed)
            Environment.setExternalStorageState(Environment.MEDIA_REMOVED);
            
            File cacheDir = driver.getCacheDirectory(context);
            
            assertNotNull(cacheDir);
            // バグがある場合(Misuse)、SDカードの状態チェックが不十分で
            // 存在しない外部パスを返してしまうか、例外を投げる可能性があります。
            // 正しい挙動(Fixed/Original)は、内部ストレージ(internal-cache)を返すことです。
            assertTrue(cacheDir.getAbsolutePath().contains("android-stub-internal-cache"),
                "Should fallback to INTERNAL cache when SD card is removed. Got: " + cacheDir.getAbsolutePath());
        }

        @Test
        @DisplayName("Reproduction: Returns INTERNAL cache dir when Permission DENIED")
        void testGetCacheDir_NoPermission() throws Exception {
            Driver driver = getTargetDriver();
            Context context = new Context();

            // SDカード: あり
            Environment.setExternalStorageState(Environment.MEDIA_MOUNTED);
            // 権限: なし
            context.setPermissionGranted(false);
            
            File cacheDir = driver.getCacheDirectory(context);
            
            assertNotNull(cacheDir);
            assertTrue(cacheDir.getAbsolutePath().contains("android-stub-internal-cache"),
                "Should fallback to INTERNAL cache when permission denied. Got: " + cacheDir.getAbsolutePath());
        }
    }

    // =========================================================
    //  Implementations
    // =========================================================

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver(androiduil._1.original.StorageUtils.class);
        }
    }

    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver(androiduil._1.misuse.StorageUtils.class);
        }
    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver(androiduil._1.fixed.StorageUtils.class);
        }
    }
}