# LLM自動修正タスクガイド

## 概要

MuBenchデータセットに対してLLMによるバグ修正を実行し、結果を保存するタスク。

## ユーザーからの指示形式

```
<プロジェクト名>のLLM修正を実行して
```

例: `ivantrendafilov-confucius/93 のLLM修正を実行して`

## タスクの進め方

### Step 1: 対象プロジェクトの確認

```bash
ls datasets/<project>/<case>/
```

必要なファイルを確認：
- `misuse.yml` - バグの位置と種類の情報
- `misuse.java` - バグを含むソースコード
- `original.java` - 元のコード（参照用）

### Step 2: misuse.yml からバグ情報を抽出

```yaml
location:
  file: "パッケージパス/ファイル名.java"
  method: "メソッド名(引数型)"
api:
  method: "java.lang.XXX.parseXXX"  # Bug Typeに使用
```

**抽出する情報：**
- **Bug Location**: `file` と `method` の値
- **Bug Type**: `api.method` の値

### Step 3: プロンプトファイルの作成

`scripts/prompts/fix-prompt.md` を作成：

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

### Step 4: LLM Runner の実行

```bash
cd scripts
python llm_runner.py
```

結果は `scripts/llm-results/YYYYMMDD_HHMMSS/result.txt` に保存される。

### Step 5: 結果の保存

```bash
# プロンプトを保存
cp scripts/prompts/fix-prompt.md datasets/<project>/<case>/prompt.md

# Javaコードを抽出して保存（```java と ``` のマーカーを除去）
# 行番号は result.txt の内容によって調整
sed -n '2,XXXp' scripts/llm-results/<timestamp>/result.txt > datasets/<project>/<case>/fixed.java

# プロンプトファイルを削除（次の処理のため）
rm scripts/prompts/fix-prompt.md
```

### Step 6: 結果の確認

1. `fixed.java` が正しいJavaコードか確認
2. 先頭/末尾に余分な文字がないか確認
3. パッケージ宣言が正しいか確認

## Context セクションのパターン例

### NumberFormatException 関連
```markdown
**Bug Type**: missing/exception_handling - `Config.java` calls `java.lang.Integer.parseInt` without first checking whether the argument parses. This leads to an uncaught `NumberFormatException`.
```

### UnsupportedEncodingException 関連
```markdown
**Bug Type**: missing/exception_handling - `Service.java` calls `String.getBytes(String)` without handling potential `UnsupportedEncodingException`.
```

### Cipher関連
```markdown
**Bug Type**: api_misuse/wrong_mode - `Service.java` uses `Cipher.ENCRYPT_MODE` instead of `Cipher.DECRYPT_MODE` in decryption operation.
```

### リソースリーク
```markdown
**Bug Type**: missing/resource_close - `Wrapper.java` does not close `DataOutputStream` after use, leading to resource leak.
```

## 注意事項

1. **テンプレートの厳守**
   - `## Output Indicator` セクションは変更しない
   - `information:{}` ラッパーは使用しない
   - Bug Location と Bug Type は `## Context` セクションに直接記載

2. **コード抽出時の注意**
   - LLM の出力には ` ```java ` と ` ``` ` マーカーが含まれる
   - マーカーを除去して純粋な Java コードのみを保存

3. **バッチ処理**
   - 同一プロジェクトの複数ケースを連続処理する場合は、各ケースごとにプロンプトを作成・実行・保存

## ファイル構成

```
scripts/
├── llm_runner.py          # LLM API 呼び出しスクリプト
├── requirements.txt       # Python 依存パッケージ
├── prompts/
│   └── fix-prompt.md      # 入力プロンプト（一時ファイル）
└── llm-results/           # 実行結果の保存先

datasets/
└── <project>/
    └── <case>/
        ├── misuse.yml     # バグ情報（入力）
        ├── misuse.java    # バグを含むコード（入力）
        ├── original.java  # 元のコード（入力）
        ├── fixed.java     # LLM による修正（出力）
        └── prompt.md      # 使用したプロンプト（出力）
```

## 完了条件

1. `fixed.java` が生成されている
2. `prompt.md` が保存されている
3. `fixed.java` が有効なJavaコードである
4. 一時ファイル `scripts/prompts/fix-prompt.md` が削除されている
