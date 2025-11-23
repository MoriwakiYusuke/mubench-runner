package androiduil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.lang.reflect.Method;
// 外部ドライバをインポート
import androiduil._1.Driver;

// ★修正: テストコードが参照すべきモックの場所を明示的に指定します
import androiduil._1.mocks.Context;
import androiduil._1.mocks.Environment;

// 注意: このテストが依存する外部ドライバ Driver.java は
// androiduil._1 パッケージにあるため、モックもこのパッケージに依存させます。
// しかし、前回モックのパッケージを androiduil.mocks に変更したため、
// テストコードのインポートもそれに合わせます。

public class AndroiduilTest_1 {

    // =========================================================
    //  Static Helper Methods (すべて androiduil.mocks のモックを使用)
    // =========================================================

    // Contextのインスタンス生成ヘルパー
    private static Context createMockContext() {
        try {
            Class<?> clazz = Class.forName("androiduil._1.mocks.Context");
            // リフレクションを使ってモックを生成
            return (Context) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("モックContextの作成に失敗しました", e);
        }
    }

    // Contextモックの権限状態を変更するヘルパー
    private static void setPermissionGranted(Context context, boolean granted) {
        try {
            Method setter = context.getClass().getMethod("setPermissionGranted", boolean.class);
            setter.invoke(context, granted);
        } catch (Exception e) {
            throw new RuntimeException("権限設定に失敗しました", e);
        }
    }

    // Environmentモックの状態変更ヘルパー
    private static void setExternalStorageState(String state) {
        try {
            Class<?> clazz = Class.forName("androiduil._1.mocks.Environment");
            clazz.getMethod("setExternalStorageState", String.class).invoke(null, state);
        } catch (Exception e) {
            throw new RuntimeException("ストレージ状態の設定に失敗しました", e);
        }
    }

    // Environmentモックのリセットヘルパー
    private static void resetEnvironment() {
        try {
            Class.forName("androiduil._1.mocks.Environment").getMethod("reset").invoke(null);
        } catch (Exception e) {
            throw new RuntimeException("Environmentモックのリセットに失敗しました.", e);
        }
    }


    // =========================================================
    //  Test Logic
    // =========================================================
    abstract static class CommonLogic {

        abstract Driver getTargetDriver();

        @BeforeEach
        void setUp() {
            resetEnvironment();
        }

        @Test
        @DisplayName("Normal: Returns external cache dir when SD card is mounted")
        void testGetCacheDir_Mounted() throws Exception {
            Driver driver = getTargetDriver();
            Context context = createMockContext();

            setExternalStorageState(Environment.MEDIA_MOUNTED);
            
            File cacheDir = driver.getCacheDirectory(context);
            
            assertNotNull(cacheDir);
            assertTrue(cacheDir.getAbsolutePath().contains("android-stub-external"));
        }

        @Test
        @DisplayName("Reproduction: Returns INTERNAL cache dir when SD card is REMOVED")
        void testGetCacheDir_Removed() throws Exception {
            Driver driver = getTargetDriver();
            Context context = createMockContext();

            setExternalStorageState(Environment.MEDIA_REMOVED);
            
            File cacheDir = driver.getCacheDirectory(context);
            
            assertNotNull(cacheDir);
            assertTrue(cacheDir.getAbsolutePath().contains("android-stub-internal-cache"));
        }

        @Test
        @DisplayName("Reproduction: Returns INTERNAL cache dir when Permission DENIED")
        void testGetCacheDir_NoPermission() throws Exception {
            Driver driver = getTargetDriver();
            Context context = createMockContext();

            setExternalStorageState(Environment.MEDIA_MOUNTED);
            setPermissionGranted(context, false);
            
            File cacheDir = driver.getCacheDirectory(context);
            
            assertNotNull(cacheDir);
            assertTrue(cacheDir.getAbsolutePath().contains("android-stub-internal-cache"));
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