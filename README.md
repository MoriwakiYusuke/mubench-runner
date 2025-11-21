# mubench-runner

## リポジトリの説明
MuBench 由来の Java コード片を対象に、LLM によるバグ修正提案と既存テストの検証を自動化するためのランナーです。`scripts/llm_runner.py` で OpenAI API を用いた修正案の生成とログ保存を行い、`gradlew test` で Java テストスイートを実行します。

## 要件
- Python 3.10 以上
- pip または互換パッケージマネージャ
- OpenAI API キー (`OPENAI_API_KEY`)
- macOS または Linux 環境 (Windows でも WSL 等で動作可能)
- Gradle Wrapper が自動取得する JDK (ツールチェーンで Java 25 を利用)

## セットアップ
1. リポジトリを取得します。
	```bash
	git clone https://github.com/MoriwakiYusuke/mubench-runner.git
	cd mubench-runner
	```
2. Python 仮想環境を作成し、有効化します。
	```bash
	python3 -m venv venv
	source venv/bin/activate
	```
3. 依存パッケージをインストールします。
	```bash
	pip install -r scripts/requirements.txt
	```
4. `.env` を作成し、OpenAI API キーを設定します。
	```bash
	echo "OPENAI_API_KEY=sk-..." > .env
	```
5. LLM 推論を実行して結果を保存します。
	```bash
	python scripts/llm_runner.py
	```
6. Java 側テストで修正案を検証します。
	```bash
	./gradlew test
	```

## ディレクトリ構成
```
.
├── datasets/                 # MuBench ベンチマークの対象コードとメタデータ
├── scripts/                  # LLM 推論や評価の補助スクリプト
│   ├── llm_runner.py
│   └── requirements.txt
├── src/
│   ├── main/java/            # ベースコードと修正案 (original/misuse/fixed)
│   └── test/java/            # テストスイート
├── build.gradle.kts          # Gradle 設定 (Java 25, JUnit 5)
├── gradlew / gradlew.bat     # プラットフォーム別 Gradle ラッパー
└── README.md
```

LLM 推論結果は `scripts/llm_runner.py` 実行時に `scripts/llm-results/` 以下へタイムスタンプ付きで保存され、Gradle テスト結果は `build/reports/tests/` に出力されます。
