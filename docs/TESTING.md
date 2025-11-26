# テスト

## 実行方法

```bash
# 全テスト実行
./gradlew test

# 特定プロジェクトのテスト
./gradlew test --tests "ivantrendafilov_confucius.*"
```

## テスト結果

```
build/reports/tests/test/index.html
```

## テストファイル配置

```
src/test/java/<project>/
└── <Project>Test_<case>.java
```

## テストの仕組み

- テストは Driver.java を経由して各バリアント（original/misuse/fixed）のコードを検証する
- ソースコードを静的解析し、特定のパターン（例外ハンドリング等）の有無を確認する

## テスト対象のバリアント

| バリアント | 期待結果 | 状態 |
|-----------|---------|------|
| original | 通過 | 有効 |
| misuse | 失敗 | コメントアウト |
| fixed | 通過 | 有効（通らない場合はコメントアウト） |

- **misuse**: バグを含むため必ず失敗する。テストでは常にコメントアウト。
- **fixed**: 修正済みのため通過することを期待。通らない場合はコメントアウトしている。
