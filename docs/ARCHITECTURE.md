# プロジェクト構造

## ディレクトリ構成

```
src/main/java/<project>/_<case>/
├── original/          # 元コード
├── misuse/            # バグを含むコード
├── fixed/             # 修正済みコード
├── requirements/      # 依存クラス（※1）
├── mocks/             # ダミー実装（※2）
└── Driver.java        # エントリポイント
```

### 注記

- **※1 `requirements/`**: 元プロジェクトから依存クラスを取得し、パッケージ宣言・インポート文・public クラス修飾子のみ修正。すべてのケースに存在するわけではない。
- **※2 `mocks/`**: ビルドを通すためのダミー実装や簡易実装。すべてのケースに存在するわけではない。

## コード修正方針

`src/main/java/` 配下のコードは、datasets/ のコードをビルド可能にするため以下のみ修正：

- パッケージ宣言
- インポート文
- public クラス修飾子

ロジックは変更していない。

## 実装済みプロジェクト

`src/main/java/` に実装されているプロジェクト：

| プロジェクト | ケース数 |
|-------------|---------|
| adempiere | 2 |
| alibaba_druid | 2 |
| android_rcs_rcsjta | 1 |
| androiduil | 1 |
| apache_gora | 2 |
| asterisk_java | 2 |
| calligraphy | 2 |
| cego | 1 |
| gnucrasha | 2 |
| hoverruan_weiboclient4j | 1 |
| ivantrendafilov_confucius | 9 |
| jmrtd | 2 |
| jriecken_gae_java_mini_profiler | 1 |
| lnreadera | 2 |
| logblock_logblock_2 | 1 |
| mqtt | 1 |
| onosendai | 1 |
| openaiab | 1 |
| pawotag | 1 |
| rhino | 1 |
| screen_notifications | 1 |
| tap_apps | 1 |
| tbuktu_ntru | 4 |
| testng | 5 |
| thomas_s_b_visualee | 3 |
| tucanmobile | 1 |
| ushahidia | 1 |
| wordpressa | 2 |

### 例外事項

#### thomas_s_b_visualee

Javaの型システム制約により、パッケージ/インポート変更のみでは解決不可能なケースがある。

**問題**: `Examiner.java`内の`for (Examiner examiner : JavaSourceInspector.getInstance().getExaminers())`で、ループ変数の型`Examiner`が同一パッケージ内の自クラスを参照している。各バリアントを異なるパッケージに配置すると、`JavaSourceInspector.getExaminers()`の戻り値`List<Examiner>`の型と一致しなくなる。

**対応**: for-each文のループ変数の型を完全修飾名に変更。ガイドライン例外として許容。
```java
// NOTE: 型を完全修飾名に変更。Javaの型システム制約により、パッケージ/インポート変更のみでは解決不可能なためガイドライン例外として許容。
for (thomas_s_b_visualee._29.mocks.examiner.Examiner examiner : JavaSourceInspector.getInstance().getExaminers()) {
```

## パッケージ命名規則

| datasets/ | src/main/java/ |
|-----------|----------------|
| `ivantrendafilov-confucius/100/` | `ivantrendafilov_confucius/_100/` |
| `alibaba-druid/1/` | `alibaba_druid/_1/` |

- ハイフン `-` → アンダースコア `_`
- ケース番号の前に `_` を追加

## ビルド設定

- Java 17（Gradle Toolchain）
- JUnit Jupiter 5.10.2
- UTF-8 エンコーディング

## Driver.java の役割

各ケースの `Driver.java` は、バリアント（original/misuse/fixed）を切り替えるためのエントリポイント。

```java
public class Driver {
    private static final String BASE_PACKAGE = "ivantrendafilov_confucius._98";

    public Driver(String variant, Properties properties) throws Exception {
        String className = BASE_PACKAGE + "." + variant + ".AbstractConfiguration";
        Class<?> clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor(InputStream.class, String.class);
        this.configInstance = (Configurable) constructor.newInstance(inputStream, null);
    }
}
```

- リフレクションを使用してバリアントのクラスを動的にロード
- 共通インターフェース経由で各バリアントのメソッドを呼び出し可能
- テストコードから統一的にアクセスできる

## テストクラスの動作

テストクラスはソースコードを静的解析し、バグ修正パターンの有無を検証する。

```java
public class Ivantrendafilov_confuciusTest_98 {

    abstract static class CommonLogic {
        abstract String getSourceFilePath();

        @Test
        void testSourceCodeHandlesNumberFormatException() throws Exception {
            String sourceCode = Files.readString(Paths.get(getSourceFilePath()));
            // メソッド内に NumberFormatException の catch があるか検証
            boolean hasHandling = methodBody.contains("catch (NumberFormatException");
            assertTrue(hasHandling, "...");
        }
    }

    @Nested
    class Original extends CommonLogic {
        @Override
        String getSourceFilePath() {
            return "src/main/java/.../original/AbstractConfiguration.java";
        }
    }

    @Nested
    class Fixed extends CommonLogic {
        @Override
        String getSourceFilePath() {
            return "src/main/java/.../fixed/AbstractConfiguration.java";
        }
    }
}
```

### テストの仕組み

1. `CommonLogic` に共通のテストロジックを定義
2. `@Nested` クラスで各バリアントのソースファイルパスを指定
3. ソースコードを文字列として読み込み、特定パターン（例外ハンドリング等）の有無を検証
4. original と fixed は検証に通過し、misuse は失敗することを期待

### Misuse のテスト

misuse バリアントはバグを含むため、テストは失敗する。通常はコメントアウトまたは `@Disabled` で無効化している。
