# ガイドライン遵守チェックタスク

データセット実行環境構築後、以下のガイドライン違反がないか確認するチェックリスト。

## チェック項目

### 1. ソースファイルの出所確認
- [ ] `original/`, `misuse/`, `fixed/` のソースファイルが **datasets/** からコピーされているか
- [ ] オリジナルのソースコードが改変されていないか（package宣言とimport文のみ変更可）
- [ ] 独自のカスタムクラス（例：CipherHelper.java等）を作成していないか

### 2. Driverクラスの要件
- [ ] **元クラスのpublic/protectedメソッド数 ≤ Driverのpublicメソッド数** を満たしているか
- [ ] 元クラスの全メソッドにアクセス可能か（リフレクション経由含む）

### 3. mocks/ディレクトリの要件
- [ ] mocksのクラスは **独自に作成** されているか（元ソースからのコピーではない）
- [ ] 依存関係を満たす最小限のスタブ実装になっているか

### 4. テストの要件
- [ ] CommonCases構造を使用しているか
- [ ] Original/Fixed → テスト全通過
- [ ] Misuse → バグ検出テストが失敗すること

## 確認コマンド例

```bash
# 元クラスとDriverのメソッド数比較
grep -c "public \|protected " src/main/java/<project>/_<case>/original/<ClassName>.java
grep -c "public " src/main/java/<project>/_<case>/Driver.java

# テスト実行
./gradlew test --tests "<project>.*"
```

## 違反が見つかった場合の対応

1. カスタムクラスを作成していた場合 → 削除し、datasets/からオリジナルをコピー
2. Driverのメソッド不足 → リフレクション経由のメソッドを追加
3. テスト失敗 → mocksの実装を修正

## 参照ドキュメント
- `docs/TESTING.md` - 全体的なテスト方針
- `docs/DATASETS.md` - データセット構造
- `docs/TEST_CLASSIFICATION.md` - テスト分類一覧
