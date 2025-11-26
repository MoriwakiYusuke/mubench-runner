# MuBench LLM Runner 処理ガイド

このドキュメントは、`datasets/` 内のプロジェクトを処理し、LLMによるバグ修正を実行する手順を説明します。

## 概要

各プロジェクトフォルダには以下のファイルが含まれています：
- `misuse.yml` - バグの位置と種類の情報
- `misuse.java` - バグを含むソースコード
- `fix.java` - 正解の修正コード（参照用）
- `original.java` - 元のコード

処理後、以下のファイルが生成されます：
- `fixed.java` - LLMによる修正コード
- `prompt.md` - 使用したプロンプト

---

## 手順

### 1. 環境準備

```bash
cd /home/moriwaki-y/ritsumei/llm/mubench-runner/scripts
source venv/bin/activate
```

### 2. misuse.yml の確認

各プロジェクトの `misuse.yml` を読み、以下の情報を抽出します：

```yaml
location:
  file: "パッケージパス/ファイル名.java"
  method: "メソッド名(引数型)"
api:
  method: "java.lang.XXX.parseXXX"  # Bug Typeに使用
```

**抽出する情報：**
- **Bug Location**: `file` と `method` の値
- **Bug Type**: `api.method` の値（例外ハンドリング不足の場合は `NumberFormatException` 関連）

### 3. プロンプトファイルの作成

`scripts/prompts/fix-prompt.md` を以下のテンプレートで作成します：

```markdown
## Instruction
You are a software engineer specializing in REST API.
Use the guidelines below to make any necessary modifications.

### Modification Procedure
0. First, familiarise yourself with the following steps and ### Notes.
1. Check the technical specifications of the Java API that you have studied or in the official documentation. If you don't know, output the ### Input Code as it is.
2. Based on the technical specifications of the Java API you have reviewed in step 1, identify the code according to the deprecated specifications contained in the ### Input Code. In this case, the deprecated specifications are the Java API calls that have been deprecated. If no code according to the deprecated specification is found, identify code that is not based on best practice. If you are not sure, output the ### Input Code as it is.
3. If you find code according to the deprecated specification or not based on best practice in step 2, check the technical specifications in the Java API that you have studied or in the official documentation. If you are not sure, output the ### Input Code as it is.
4. With attention to the points listed in ### Notes below, modify the code identified in step 2 to follow the recommended specification analysed in step 3.
5. Verify again that the modified code works correctly.
6. If you determine that it works correctly, output the modified code.
7. If it is judged to fail, output the ### Input Code as it is.
8. If you are not sure, output the ### Input Code as it is.

### Notes.
- You must follow the ## Context.

## Input Code
```java
【ここに misuse.java の内容を貼り付け】
```

## Context

**Bug Location**: File `【ファイルパス】`, Method `【メソッド名】`
**Bug Type**: missing/exception_handling - `【ファイル名】` calls `【API名】` without first checking whether the argument parses. This leads to an uncaught `NumberFormatException`.

Can you identify and fix it?

## Output Indicator
Update the ### Input Code as per the latest API specification, making necessary modifications.
Ensure the structure and format remain as close as possible to the original, but deprecated code must be updated. Output the all revised code without additional explanations or comments.
```

### 4. LLM Runner の実行

```bash
cd /home/moriwaki-y/ritsumei/llm/mubench-runner/scripts
python llm_runner.py
```

実行後、`llm-results/YYYYMMDD_HHMMSS/result.txt` に結果が保存されます。

### 5. 結果の保存

```bash
# プロンプトを保存
cp prompts/fix-prompt.md ../datasets/【プロジェクト名】/【番号】/prompt.md

# Javaコードを抽出して保存（```java と ``` のマーカーを除去）
sed -n '2,318p' llm-results/【タイムスタンプ】/result.txt > ../datasets/【プロジェクト名】/【番号】/fixed.java

# プロンプトファイルを削除（次の処理のため）
rm prompts/fix-prompt.md
```

**注意:** `sed` の行番号は result.txt の内容によって調整が必要です。`wc -l` で行数を確認してください。

---

## バッチ処理の例

複数プロジェクトを処理する場合のワークフロー：

```bash
# 対象プロジェクト一覧
PROJECTS=(93 94 95 96 97 98 99 100 101)
BASE_DIR="/home/moriwaki-y/ritsumei/llm/mubench-runner"

for proj in "${PROJECTS[@]}"; do
    echo "Processing project $proj..."
    
    # 1. misuse.yml を確認してプロンプトを作成（手動またはスクリプト）
    # 2. llm_runner.py を実行
    # 3. 結果を保存
done
```

---

## Context セクションのパターン例

### NumberFormatException 関連

```markdown
**Bug Location**: File `org/example/Config.java`, Method `getIntValue(String)`
**Bug Type**: missing/exception_handling - `Config.java` calls `java.lang.Integer.parseInt` without first checking whether the argument parses. This leads to an uncaught `NumberFormatException`.
```

### その他の例外ハンドリング

```markdown
**Bug Location**: File `org/example/FileUtil.java`, Method `readFile(String)`
**Bug Type**: missing/exception_handling - `FileUtil.java` calls `java.io.FileInputStream.<init>` without handling potential `FileNotFoundException`.
```

### Null チェック不足

```markdown
**Bug Location**: File `org/example/Service.java`, Method `process(Object)`
**Bug Type**: missing/null_check - `Service.java` calls method on potentially null object without null check.
```

---

## 注意事項

1. **テンプレートの厳守**
   - `## Output Indicator` セクションは変更しない
   - `information:{}` ラッパーは使用しない
   - Bug Location と Bug Type は `## Context` セクションに直接記載

2. **コード抽出時の注意**
   - LLM の出力には ` ```java ` と ` ``` ` マーカーが含まれる
   - マーカーを除去して純粋な Java コードのみを保存

3. **結果の確認**
   - `fixed.java` が正しい Java コードであることを確認
   - 先頭/末尾に余分な文字がないことを確認

---

## ファイル構成

```
scripts/
├── llm_runner.py          # LLM API 呼び出しスクリプト
├── requirements.txt       # Python 依存パッケージ
├── venv/                  # Python 仮想環境
├── prompts/
│   └── fix-prompt.md      # 入力プロンプト（一時ファイル）
├── llm-results/           # 実行結果の保存先
│   └── YYYYMMDD_HHMMSS/
│       └── result.txt
└── PROCESSING_GUIDE.md    # このガイド

datasets/
└── 【プロジェクト名】/
    └── 【番号】/
        ├── misuse.yml     # バグ情報
        ├── misuse.java    # バグを含むコード
        ├── fix.java       # 正解の修正
        ├── original.java  # 元のコード
        ├── fixed.java     # LLM による修正（生成）
        └── prompt.md      # 使用したプロンプト（生成）
```
