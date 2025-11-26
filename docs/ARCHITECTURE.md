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
