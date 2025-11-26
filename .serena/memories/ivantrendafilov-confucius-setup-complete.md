# ivantrendafilov-confucius プロジェクト MuBenchセットアップ完了

## 概要
- **日付**: 2025年11月26日
- **プロジェクト**: ivantrendafilov-confucius (Confucius設定ライブラリ)
- **ケース番号**: 93-101 (全9ケース)
- **バグタイプ**: NumberFormatExceptionの適切な例外ハンドリング欠如

## ディレクトリ構造

各ケース（_93〜_101）は以下の構造を持つ：

```
src/main/java/ivantrendafilov_confucius/_XX/
├── original/
│   └── AbstractConfiguration.java   # データセット（元のコード）
├── misuse/
│   └── AbstractConfiguration.java   # データセット（バグあり）
├── fixed/
│   └── AbstractConfiguration.java   # データセット（修正済み）
├── requirements/                    # Confuciusプロジェクトからの依存クラス
│   ├── Configurable.java
│   ├── ConfigurationException.java
│   ├── ConfigurationDataProvider.java
│   ├── FileConfigurationDataProvider.java
│   ├── StreamConfigurationDataProvider.java
│   ├── Parser.java
│   └── Utils.java
├── mocks/                          # テスト用モッククラス
│   ├── InjectableConfigurationOriginal.java
│   ├── InjectableConfigurationMisuse.java
│   └── InjectableConfigurationFixed.java
└── Driver.java                     # テストドライバ
```

## ケース詳細

| ケース | ターゲットメソッド | バグ内容 | Fixed動作 |
|--------|-------------------|----------|-----------|
| 93 | `getByteValue(String)` | NFE未ハンドル | ConfigurationExceptionスロー |
| 94 | `getByteValue(String, byte)` | NFE未ハンドル | デフォルト値を返す（警告ログ） |
| 95 | `getByteList(String, String)` | NFE未ハンドル | ConfigurationExceptionスロー |
| 96 | `getLongValue(String)` | NFE未ハンドル | ConfigurationExceptionスロー |
| 97 | `getLongValue(String, long)` | NFE未ハンドル | デフォルト値を返す（警告ログ） |
| 98 | `getLongList(String, String)` | NFE未ハンドル | ConfigurationExceptionスロー |
| 99 | `getShortValue(String)` | NFE未ハンドル | ConfigurationExceptionスロー |
| 100 | `getShortValue(String, short)` | NFE未ハンドル | デフォルト値を返す（警告ログ） |
| 101 | `getShortList(String, String)` | NFE未ハンドル | 無効値をスキップ（警告ログ） |

※ NFE = NumberFormatException

## 依存関係

### build.gradle.kts への追加
```kotlin
implementation("org.slf4j:slf4j-api:2.0.9")
implementation("org.slf4j:slf4j-simple:2.0.9")
```

### 外部プロジェクト参照
- 元プロジェクト: `/home/moriwaki-y/ritsumei/llm/mubench-runner/temp/Confucius`
- requirements/内のクラスは元プロジェクトから取得し、パッケージ宣言のみ変更

## テストクラス

テストファイル: `src/test/java/ivantrendafilov_confucius/Ivantrendafilov_confuciusTest_XX.java`

テスト構造:
- `CommonLogic` 抽象クラス: 共通テストロジック
- `OriginalTest`: original版のテスト
- `MisuseTest`: misuse版のテスト（バグ再現）
- `FixedTest`: fixed版のテスト

## 重要な実装ノート

### Driverクラスの動作
1. PropertiesをInputStreamに変換
2. 各バリアントのInjectableConfigurationをインスタンス化
3. Configurableインターフェース経由でメソッド呼び出し

### データセット変換時の修正
- 元データセットの `getBoolean List` → `getBooleanList` (スペース削除)

### パッケージ変換
- 元: `org.trendafilov.confucius.core`
- 変換後: `ivantrendafilov_confucius._XX.{original|misuse|fixed}`

### requirements内クラスのpublic化
以下のクラスは元がpackage-privateだったためpublic化:
- ConfigurationDataProvider
- FileConfigurationDataProvider
- StreamConfigurationDataProvider
- Parser

## テスト実行結果
- 全105テスト PASSED
- コマンド: `./gradlew test --tests "ivantrendafilov_confucius.*"`
