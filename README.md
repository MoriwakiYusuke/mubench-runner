# mubench-runner

MuBench ベンチマークデータセットを用いた Java API 誤用パターンの検証・解析基盤です。

## 概要

このリポジトリは、MuBench（API誤用検出ベンチマーク）由来のコードを対象に、バグ検出・修正の研究を支援します。各ケースは original（元コード）/ misuse（バグあり）/ fixed（修正済み）の3バリアントで構成され、JUnit 5 テストで検証可能です。

## 要件

- Java 17 以上（Gradle Toolchain で自動取得）
- macOS / Linux / Windows（WSL）

## クイックスタート

```bash
# リポジトリ取得
git clone https://github.com/MoriwakiYusuke/mubench-runner.git
cd mubench-runner

# ビルドとテスト実行
./gradlew build

# ビルドに失敗した場合（依存JARを手動取得）
./gradlew dependencies --configuration compileClasspath
# または Maven Central から直接取得:
# https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar

# テストのみ実行
./gradlew test

# テスト結果を確認（build/reports/tests/test/index.html）
```

## ディレクトリ構成

```
.
├── datasets/                 # MuBench メタデータ（出典から変更なし）
│   └── <project>/
│       └── <case>/
│           ├── misuse.yml    # バグ情報
│           ├── original.java # 元コード
│           ├── misuse.java   # バグあり
│           └── fix.java      # 正解修正
├── src/
│   ├── main/java/            # テスト対象コード（※1）
│   │   └── <project>/
│   │       └── _<case>/
│   │           ├── original/
│   │           ├── misuse/
│   │           ├── fixed/
│   │           ├── requirements/ # （※2）
│   │           ├── mocks/        # （※3）
│   │           └── Driver.java
│   └── test/java/            # JUnit 5 テストスイート
├── docs/                     # ドキュメント
│   ├── ARCHITECTURE.md       # プロジェクト構造
│   ├── DATASETS.md           # データセット一覧
│   ├── TESTING.md            # テスト
│   └── BINARY_EXPORT.md      # バイナリエクスポート
├── build.gradle.kts          # Gradle 設定
└── gradlew / gradlew.bat     # Gradle Wrapper
```

### 注記

- **※1 `src/main/java/`**: datasets/ のコードをビルド可能にするため、パッケージ宣言・インポート文・public クラス修飾子のみ修正しています。ロジックは変更していません。
- **※2 `requirements/`**: 元プロジェクトから依存クラスを取得し、同様にパッケージ宣言・インポート文・public クラス修飾子のみ修正しています。すべてのケースに存在するわけではありません。
- **※3 `mocks/`**: ビルドを通すためのダミー実装や簡易実装です。すべてのケースに存在するわけではありません。

## ドキュメント

| ドキュメント | 説明 |
|-------------|------|
| [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md) | プロジェクト構造 |
| [docs/DATASETS.md](docs/DATASETS.md) | データセット一覧 |
| [docs/TESTING.md](docs/TESTING.md) | テスト |
| [docs/BINARY_EXPORT.md](docs/BINARY_EXPORT.md) | バイナリエクスポート |

## バイナリエクスポート

クラスファイルをエクスポートできます。

```bash
# 全エクスポート（推奨）
./gradlew exportBinaries
```

詳細は [docs/BINARY_EXPORT.md](docs/BINARY_EXPORT.md) を参照してください。

## ライセンス

各データセットは元プロジェクトのライセンスに従います。
