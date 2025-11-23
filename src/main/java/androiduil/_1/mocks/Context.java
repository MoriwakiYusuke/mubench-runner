package androiduil._1.mocks;

import java.io.File;

public class Context {
    // 権限状態を保持するフィールド
    private boolean isPermissionGranted = true;

    public File getCacheDir() {
        File file = new File(System.getProperty("java.io.tmpdir"), "android-stub-internal-cache");
        file.mkdirs();
        return file;
    }

    public String getPackageName() {
        return "com.example.mockapp";
    }

    public int checkCallingOrSelfPermission(String permission) {
        // フィールドの値に応じて結果を変える
        return isPermissionGranted ? PackageManager.PERMISSION_GRANTED : PackageManager.PERMISSION_DENIED;
    }

    // ▼ 追加: テスト用のHelperメソッド
    public void setPermissionGranted(boolean granted) {
        this.isPermissionGranted = granted;
    }
}