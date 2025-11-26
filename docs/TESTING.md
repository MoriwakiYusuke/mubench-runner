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
