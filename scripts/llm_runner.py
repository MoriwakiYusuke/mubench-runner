import os
from dotenv import load_dotenv
from openai import OpenAI
from datetime import datetime
import shutil
from dotenv import find_dotenv

loaded = load_dotenv(find_dotenv())

client = OpenAI()

input_file = "./prompts/fix-prompt.md"
# input_file = "./prompts/test-make-prompt2.md"

def main():
    # APIキー確認
    if not os.getenv("OPENAI_API_KEY"):
        print("Error: OPENAI_API_KEY が見つかりません. .envを確認してください.")
        return

    # 2. タイムスタンプ付きの出力ディレクトリを作成
    timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
    output_dir = os.path.join("llm-results", timestamp)

    os.makedirs(output_dir, exist_ok=True)
    print(f"Created output directory: {output_dir}")

    try:
        # 3. ファイル読み込み
        print(f"Reading {input_file}...")
        with open(input_file, "r", encoding="utf-8") as f:
            prompt_content = f.read()

        # 4. APIリクエスト
        print("Requesting to OpenAI...")
        completion = client.chat.completions.create(
            model="gpt-5.1-2025-11-13",
            messages=[
                {"role": "user", "content": prompt_content}
            ]
        )
        result_content = completion.choices[0].message.content

        # 5. 結果の保存 (テキストのみ)
        output_file = os.path.join(output_dir, "result.txt")
        with open(output_file, "w", encoding="utf-8") as f:
            f.write(result_content)

        # 6. 【追加】レスポンスそのまま(Raw JSON)を保存
        # .model_dump_json() を使うと、オブジェクトの中身を綺麗なJSON形式の文字列にしてくれます
        raw_output_file = os.path.join(output_dir, "response_raw.json")
        with open(raw_output_file, "w", encoding="utf-8") as f:
            f.write(completion.model_dump_json(indent=2))

        # 7. 使ったプロンプトも一緒に保存
        shutil.copy(input_file, os.path.join(output_dir, "prompt_log.md"))

        print(f"Done! Saved results to {output_dir}")

    except Exception as e:
        print(f"Error: {e}")


if __name__ == "__main__":
    main()
