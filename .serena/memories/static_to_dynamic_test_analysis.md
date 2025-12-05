# 静的テスト → 動的テスト 変換可能性分析

## 概要

12件の静的テストについて、動的テストへの変換が技術的に可能かを分析した結果。

## 結論サマリ

| 分類 | 件数 | 対象 |
|---|:---:|---|
| ✅ **動的化可能** | 4件 | Adempiere 1/2, AndroidRcsRcsjta 1, Jmrtd 2, Rhino 1 |
| ⚠️ **条件付き可能** | 3件 | AlibabaDruid 1/2, Jmrtd 1 |
| ❌ **困難** | 2件 | Androiduil 1, Onosendai 1 |

---

## 詳細分析

### ✅ 動的テスト化が可能（4件）

#### Adempiere 1/2 - エンコーディング漏れ
- **バグ**: `getBytes()`を引数なしで使用（プラットフォームデフォルト）
- **動的テスト案**: 非ASCII文字（日本語等）で暗号化→復号化のラウンドトリップ検証
```java
String input = "日本語テスト文字列";
byte[] encrypted = driver.encrypt(input);
String decrypted = driver.decrypt(encrypted);
assertEquals(input, decrypted); // misuse版は環境依存で失敗
```

#### AndroidRcsRcsjta 1 - エンコーディング漏れ
- **バグ**: `getBytes()`を引数なしで使用
- **動的テスト案**: 非ASCII文字でMAC計算、期待値と比較

#### Jmrtd 2 - ENCRYPT_MODE vs DECRYPT_MODE
- **バグ**: 復号化に`Cipher.ENCRYPT_MODE`を使用
- **動的テスト案**: 暗号化→復号化ラウンドトリップ
```java
byte[] original = "test".getBytes();
byte[] encrypted = encrypt(original);
byte[] decrypted = decrypt(encrypted);
assertArrayEquals(original, decrypted); // misuse版は失敗
```

#### Rhino 1 - initFunction重複呼び出し
- **バグ**: `IRFactory.initFunction()`が同一実行パスで2回呼び出され無限ループ
- **動的テスト案**: 特定のネストした関数定義をパースしてタイムアウト/スタックオーバーフロー検出
```java
String code = "function outer() { function inner() {} }";
try {
    driver.parse(code);
    // misuse版は無限ループ → タイムアウトで検出
} catch (StackOverflowError e) {
    fail("重複呼び出しによる無限ループ");
}
```

---

### ⚠️ 条件付きで可能（3件）

#### AlibabaDruid 1 - Cipherインスタンス再利用
- **バグ**: `Cipher.init()`を同一インスタンスで2回呼び出し
- **問題**: IBM JDK特有の問題。Oracle/OpenJDKでは再現しない
- **条件**: IBM JDK環境でのみ動的検証可能

#### AlibabaDruid 2 - InvalidKeyException未ハンドリング
- **バグ**: 例外ハンドリングなし
- **問題**: 不正なキーで例外発生は確認可能だが、「ハンドリングしているか」の検証は困難
- **条件**: テスト意図が「例外発生」から「例外ハンドリング」に変わる

#### Jmrtd 1 - DataOutputStream.close()漏れ
- **バグ**: リソースリーク
- **問題**: カスタムOutputStreamでclose()呼び出しをトラッキング可能だが実装が複雑
- **条件**: スパイパターンの実装が必要

---

### ❌ 動的テスト化が困難（2件）

#### Androiduil 1 - NullPointerException未ハンドリング
- **バグ**: `Environment.getExternalStorageState()`がNPEをスローする可能性
- **困難な理由**: 
  - Androidランタイム依存
  - nullを返す状況は実機/エミュレータ依存
  - JUnit単体テストでは再現困難

#### Onosendai 1 - ApplicationContext未使用
- **バグ**: `context.getApplicationContext()`を使わずIntentFilter登録
- **困難な理由**:
  - Androidランタイム必須
  - IntentFilter登録の動作確認不可
  - モックでは「正しいコンテキストを使っているか」を動的に検証できない

---

## パターン別の動的化しやすさ

| パターン | 動的化 | 備考 |
|---|:---:|---|
| エンコーディング系 | ✅ 容易 | ラウンドトリップで検証可能 |
| 暗号モード系 | ✅ 容易 | ENCRYPT/DECRYPTの結果で検証 |
| 無限ループ/再帰系 | ✅ 可能 | タイムアウト/StackOverflowで検出 |
| 例外ハンドリング系 | ⚠️ 条件付き | 例外発生は確認可能だがハンドリング検証は困難 |
| リソースリーク系 | ⚠️ 条件付き | スパイパターン必須で複雑 |
| JDK依存系 | ⚠️ 条件付き | 特定環境でのみ再現 |
| Androidランタイム依存 | ❌ 困難 | JUnit単体テストでは不可 |

---

## 実装完了（2024年）

### ✅ 動的化完了（5件）

| プロジェクト | テスト数 | 方法 |
|---|:---:|---|
| Adempiere 1 | 6テスト | UTF-8 ラウンドトリップ |
| Adempiere 2 | 6テスト | UTF-8 ラウンドトリップ |
| AndroidRcsRcsjta 1 | 5テスト | ContributionId一貫性検証 |
| Rhino 1 | 5テスト | parse() + @Timeout |
| Jmrtd 2 | 10テスト | ソース解析 + RSA暗号化/復号化 |

### 技術的注記
- **Jmrtd 2**: PassportAuthService は `SHA1WithRSA/ISO9796-2` が必要で標準JDKでは不可。独立した Cipher テストで動的検証。

---

## 推奨アクション

1. ~~**優先的に動的化**: Adempiere 1/2, AndroidRcsRcsjta 1, Jmrtd 2, Rhino 1~~ → **完了**
2. **検討**: AlibabaDruid 1/2, Jmrtd 1（実装コストと効果のバランス次第）
3. **静的テスト維持**: Androiduil 1, Onosendai 1（動的化の技術的困難性が高い）
