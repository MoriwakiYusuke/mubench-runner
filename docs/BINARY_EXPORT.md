# バイナリエクスポート

## エクスポートコマンド

```bash
# 全エクスポート
./gradlew exportBinaries

# 個別実行
./gradlew exportAll       # バリアント別 JAR
./gradlew exportClasses   # クラスファイル
./gradlew exportPairs     # 比較ペア形式
```

## 出力先

```
build/exports/
├── variants/
│   ├── mubench-original-1.0.jar
│   ├── mubench-misuse-1.0.jar
│   └── mubench-fixed-1.0.jar
├── classes/
│   └── <project>/_<case>/
│       ├── original/
│       ├── misuse/
│       ├── fixed/
│       └── requirements/
└── pairs/
    └── <project>_<case>/
        ├── original/
        ├── misuse/
        └── fixed/
```
