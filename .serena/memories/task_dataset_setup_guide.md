# データセット実行環境構築ガイド

## 概要

MuBenchデータセットのプロジェクトを `src/main/java/` 配下にビルド・テスト可能な形で構築するタスク。

## ユーザーからの指示形式

```
<プロジェクト名>に対して他と同様に実行環境を構築して
```

例: `jmrtdに対して他と同様に実行環境を構築して`

## 重要な制約

### Driverクラス経由のテスト実行
**テストは必ずDriver.javaを経由して各バリアント（original/misuse/fixed）のコードを検証する。**

- テストクラスから直接バリアントのクラスをインポート・インスタンス化しない
- Driverがバリアントのクラス名を受け取り、リフレクションまたはソース解析で検証
- これにより、テストロジックを共通化し、バリアント切り替えを容易にする

## タスクの進め方

### Step 0: 既存実装の確認（必須）

タスク開始前に、既存の実行環境を確認して実装パターンを把握する：

```bash
# 既存プロジェクト一覧
ls src/main/java/

# 参考にすべき実装例
ls src/main/java/cego/_1/           # リフレクション実行パターン
ls src/main/java/adempiere/_1/      # ソースコード静的解析パターン
ls src/main/java/gnucrasha/_1a/     # Android Activityテスト
```

特に以下のファイルを確認：
- `Driver.java` - エントリポイントの設計
- `requirements/` - 依存クラスの配置方法
- `mocks/` - ダミー実装の作り方
- 対応するテストクラス（`src/test/java/`）

### Step 1: データセットの確認

```bash
ls datasets/<project>/
```

各ケース番号のディレクトリを確認し、以下のファイル構成を把握：
- `original.java` - 正常なコード
- `misuse.java` - バグを含むコード
- `misuse.yml` - バグの説明（メソッド名、行番号等）
- `fixed.java` - LLMによる修正コード
- `prompt.md` - LLMへのプロンプト

### Step 2: ディレクトリ構造の作成

```
src/main/java/<project>/_<case>/
├── original/          # datasets/<project>/<case>/original.java を配置
├── misuse/            # datasets/<project>/<case>/misuse.java を配置
├── fixed/             # datasets/<project>/<case>/fixed.java を配置
├── requirements/      # 依存クラス（コンパイルに必要なもの）
├── mocks/             # ダミー実装（必要に応じて）
└── Driver.java        # テスト用エントリポイント
```

### Step 3: コード変換（制約厳守）

**変更してよい箇所（これ以外は絶対に変更しない）:**
- パッケージ宣言 (`package sos.mrtd;` → `package jmrtd._1.original;`)
- インポート文 (依存先をrequirementsに合わせる)
- publicクラス修飾子（必要に応じて）

**変更してはいけない箇所:**
- ロジック
- コメント（追加も削除も不可）
- 変数名、メソッド名
- その他すべてのコード

### Step 4: 依存クラスの解決

1. コンパイルエラーを確認
2. 必要な依存クラスを特定
3. **ユーザーに確認**: requirements/mocks/の使い分けについて提案

#### ユーザーへの確認テンプレート

```
依存クラスの解決方法について確認させてください。

【必要な依存クラス】
- ClassName1: 〜の機能を提供
- ClassName2: 〜のインターフェース

【提案】
A. requirements/ のみで実装
   - 理由: 元プロジェクトからクラスを取得可能、ロジックに影響なし
   
B. mocks/ のみで実装
   - 理由: 依存が複雑、ダミー実装で十分テスト可能
   
C. requirements/ + mocks/ 併用
   - requirements/: ClassName1（実装が必要）
   - mocks/: ClassName2（ダミーで十分）

推奨: [A/B/C] - [理由]

どの方針で進めますか？
```

#### requirements/ vs mocks/ の判断基準

| 条件 | requirements/ | mocks/ |
|------|---------------|--------|
| 元プロジェクトから取得可能 | ✓ | |
| インターフェース/抽象クラスのみ必要 | ✓ | |
| 実際のロジックが必要 | ✓ | |
| 依存が深い（さらに依存がある） | | ✓ |
| Android/フレームワーク依存 | | ✓ |
| テストで実行されない部分 | | ✓ |
| package-private クラス | | ✓ |

#### package-private クラスの制限

元ライブラリのクラスが package-private の場合：
- Gradle 依存追加ではアクセス不可（サブパッケージからも不可）
- **解決策:** モック作成

#### Gradle 依存関係の追加

必要に応じて `build.gradle.kts` に依存を追加：

```kotlin
dependencies {
    // 例: Apache Commons
    implementation("org.apache.commons:commons-lang3:3.12.0")
}
```

**注意事項:**
- 元プロジェクトの pom.xml / build.gradle からバージョンを確認
- package-private クラスは依存追加しても使用不可
- Android SDK (`com.google.android:android`) は実行時にスタブ例外が発生（コンパイルのみ有効）

4. 決定後、requirements/ または mocks/ に配置（同じ変換制約を適用）

### Step 5: Driver.java の作成

#### テストパターンの選択基準

**基本方針: リフレクション実行パターンを優先する**

| 条件 | 推奨パターン |
|------|-------------|
| 純粋なJavaロジック、依存が少ない | リフレクション実行 |
| Android/フレームワーク依存が深い | リフレクション実行 + モック |
| 暗号/ネットワーク等、実行困難 | リフレクション実行 + モック |
| package-private 依存 | リフレクション実行 + モック |

パターンは2種類：

#### A. リフレクション実行パターン（推奨）
実行可能なケース向け（基本的にこちらを使用）

```java
public class Driver {
    private final Object instance;
    
    public Driver(String targetClassName, Object... args) {
        Class<?> clazz = Class.forName(targetClassName);
        Constructor<?> ctor = clazz.getConstructor(...);
        this.instance = ctor.newInstance(args);
    }
}
```

#### B. ソースコード静的解析パターン（極力避ける）
リフレクション実行が本当に困難な場合のみ使用

```java
public class Driver {
    private final String sourceFilePath;
    
    public Driver(String targetClassName) {
        this.sourceFilePath = "src/main/java/" + targetClassName.replace('.', '/') + ".java";
    }
    
    public String readSourceCode() throws IOException {
        return Files.readString(Paths.get(sourceFilePath));
    }
    
    // バグパターンの検出メソッド
    public boolean hasCorrectPattern() throws IOException {
        String source = readSourceCode();
        // 特定パターンの有無を検査
        return source.contains("expectedPattern");
    }
}
```

### Step 6: テストクラスの作成

```
src/test/java/<project>/<Project>Test_<case>.java
```

**必須構造:**

```java
class ProjectTest_1 {
    private static final String BASE_PACKAGE = "project._1";

    abstract static class CommonCases {
        abstract Driver driver();

        @Test
        void testBugPattern() {
            // 共通テストロジック
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {
        @Override
        Driver driver() {
            return new Driver(BASE_PACKAGE + ".original.ClassName");
        }
    }

    // Misuseは常にコメントアウト（バグがあるため必ず失敗）
    // @Nested
    // class Misuse extends CommonCases { ... }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {
        @Override
        Driver driver() {
            return new Driver(BASE_PACKAGE + ".fixed.ClassName");
        }
    }
}
```

### Step 7: ビルド確認

```bash
./gradlew test --tests "<project>.*"
```

### Step 8: データセット一致確認

```bash
# パッケージ・インポート以外が一致することを確認
diff <(grep -v "^package\|^import" datasets/<project>/<case>/original.java | grep -v "^$") \
     <(grep -v "^package\|^import" src/main/java/<project>/_<case>/original/ClassName.java | grep -v "^$")
```

## 制約条件チェックリスト

- [ ] パッケージ宣言のみ変更
- [ ] インポート文のみ変更
- [ ] ロジック変更なし
- [ ] コメント追加・削除なし
- [ ] データセットと一致確認済み
- [ ] Driverはクラス名を受け取るコンストラクタ
- [ ] テストは@Nested + abstract CommonCases構造
- [ ] Misuseはコメントアウト
- [ ] ビルド成功
- [ ] 全テストPASS

## パッケージ命名規則

| datasets/ | src/main/java/ |
|-----------|----------------|
| `project-name/1/` | `project_name/_1/` |
| `project-name/100/` | `project_name/_100/` |

- ハイフン `-` → アンダースコア `_`
- ケース番号の前に `_` を追加

## LLM失敗ケースの対応

fixedがバグを修正していない場合：

### パターンA: Fixedをコメントアウト（推奨）
テストが失敗する場合は、Misuseと同様にコメントアウトする：

```java
// Fixedはテストに失敗するためコメントアウト（LLMが正しく修正できなかった）
// @Nested
// @DisplayName("Fixed")
// class Fixed extends CommonCases {
//     @Override
//     Driver driver() {
//         return new Driver(BASE_PACKAGE + ".fixed.ClassName");
//     }
// }
```

### パターンB: 失敗を検証するテスト
LLMの失敗パターンを記録・検証したい場合：
1. テストでその失敗を検証する設計にする
2. テスト名に「LLM Failure Case」等を明記
3. CommonCasesを継承せず独自テストを書く

## 既存プロジェクト参照

実装例として以下を参照：
- `cego/_1` - リフレクション実行パターン
- `gnucrasha/_1a`, `_1b` - Android Activity テスト
- `adempiere/_1`, `_2` - ソースコード静的解析パターン
- `ivantrendafilov_confucius/_93`〜`_101` - 複数ケース

## 完了条件

1. `./gradlew build` が成功
2. 全テストPASS
3. データセットとの一致確認済み（制約遵守）
