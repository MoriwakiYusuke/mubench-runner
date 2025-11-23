package androiduil._1.mocks;

import java.io.File;

public class Environment {
    public static final String MEDIA_MOUNTED = "mounted";
    // ▼ 追加: テストで使用されている定数
    public static final String MEDIA_REMOVED = "removed";

    // 状態を保持するフィールド
    private static String currentStorageState = MEDIA_MOUNTED;

    public static String getExternalStorageState() {
        return currentStorageState;
    }

    public static File getExternalStorageDirectory() {
        File file = new File(System.getProperty("java.io.tmpdir"), "android-stub-external");
        file.mkdirs();
        return file;
    }

    // ▼ 追加: テスト用のHelperメソッド
    public static void setExternalStorageState(String state) {
        currentStorageState = state;
    }

    // ▼ 追加: テスト用のリセットメソッド
    public static void reset() {
        currentStorageState = MEDIA_MOUNTED;
    }
}