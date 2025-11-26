# MuBench ケース対応プロンプト

## タスク概要
MuBenchベンチマークの指定ケースに対して、コンパイル可能な環境を構築し、ドライバ経由でテストを作成してください。

## 前提条件
- 変更はインポート文とパッケージ宣言のみとし、ロジックの変更は行わない
- `original`, `misuse`, `fixed` の3バリアントすべてがコンパイル・テスト可能であること
- テストはドライバクラス経由で実行する

## 実施手順

### 1. ケースの確認
- `datasets/<project>/<id>/misuse.yml` を読み、バグの内容・対象ファイル・メソッドを把握する
- `original.java`, `misuse.java`, `fixed.java` の差分を確認する

### 2. 依存関係の追加
`build.gradle.kts` に必要なライブラリを追加:
```kotlin
dependencies {
    // 必要に応じて追加
    implementation("...")
}
```

### 3. パッケージ構造の整理
- `src/main/java/<project>/_<id>/` 配下に以下を配置:
  - `original/` - 修正前コード
  - `misuse/` - バグを含むコード
  - `fixed/` - 修正後コード
  - `mocks/` - モッククラス（必要に応じて）
- 各バリアントのパッケージ宣言を適切に変更
- インポート文を requirements 配下のクラスを参照するよう更新

### 4. ドライバの作成
`src/main/java/<project>/_<id>/Driver.java`:
```java
package <project>.<id>;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class Driver {
    private final Class<?> targetClass;
    // 対象メソッドへの参照

    public Driver(Class<?> targetClass) {
        // リフレクションでメソッドを取得
    }

    // 対象メソッドを呼び出すラッパーメソッド
    // 例外は適切に伝播させる
}
```

### 5. テストの作成
`src/test/java/<project>/<Project>Test_<id>.java`:
```java
package <project>;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class <Project>Test_<id> {

    abstract static class CommonLogic {
        abstract Driver getDriver();

        @Test
        void 正常系テスト() { ... }

        @Test
        void 異常系テスト() { ... }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver getDriver() {
            return new Driver(<project>.<id>.original.<TargetClass>.class);
        }
    }

    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonLogic {
        @Override
        Driver getDriver() {
            return new Driver(<project>.<id>.misuse.<TargetClass>.class);
        }
    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver getDriver() {
            return new Driver(<project>.<id>.fixed.<TargetClass>.class);
        }
    }
}
```

### 6. コンパイル・テスト確認
```bash
./gradlew compileJava
./gradlew test
```

## 注意事項
- 重複クラス（同一FQCNが複数ファイルに存在）がないことを確認
- ロジックは一切変更しない（インポート・パッケージ宣言のみ）
- テストは misuse.yml に記載されたバグを検出できる内容にする
- 各バリアントで期待される挙動の違いをテストで明示する
- **モッククラスを作成する必要がある場合は、作成前にユーザーに許可を求めること**

## 成果物
1. コンパイル可能な `original/`, `misuse/`, `fixed/` バリアント
2. 共通依存を格納した `requirements/` ディレクトリ（必要に応じて）
3. リフレクションベースの `Driver.java`
4. 3バリアントをカバーする JUnit 5 テストクラス
