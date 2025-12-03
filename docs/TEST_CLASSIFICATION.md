# テストコード分類一覧

このドキュメントでは、各テストコードが**動的テスト**か**静的テスト**かを分類しています。

## 凡例

| 記号 | 種類 | 説明 |
|:---:|---|---|
| 📝 | 静的テスト | ソースコードを`Files.readString()`等で読み込み、特定パターンの有無を`contains()`や正規表現で検査 |
| 🔄 | 動的テスト | Driver経由でメソッドを実行し、戻り値やオブジェクトの状態を`assert`で検証 |
| 📝+🔄 | 混合 | 両方のアプローチを併用 |

---

## 分類一覧表

| テストファイル | 種類 | 検証方法 |
|---|:---:|---|
| **adempiere** | | |
| `AdempiereTest_1.java` | 📝 静的 | `Files.readString()`でソースを読み、`getBytes("UTF8")`パターンを検査 |
| `AdempiereTest_2.java` | 📝 静的 | 同上 |
| **alibaba_druid** | | |
| `AlibabaDruidTest_1.java` | 📝 静的 | `cipher = Cipher.getInstance`パターンを検査 |
| `AlibabaDruidTest_2.java` | 📝 静的 | `catch (InvalidKeyException`パターンを検査 |
| **android_rcs_rcsjta** | | |
| `AndroidRcsRcsjtaTest_1.java` | 📝 静的 | `getBytes(UTF8)`パターンを検査 |
| **androiduil** | | |
| `AndroiduilTest_1.java` | 📝 静的 | `catch (NullPointerException`パターンを検査 |
| **apache_gora** | | |
| `ApacheGoraTest_56_1.java` | 🔄 動的 | `followingKey()`、`lastPossibleKey()`の戻り値を検証 |
| `ApacheGoraTest_56_2.java` | 🔄 動的 | `writeThenRead()`でPropertiesの読み書きを検証 |
| **asterisk_java** | | |
| `AsteriskJavaTest_81.java` | 🔄 動的 | `decodeEnv()`、`decodeResult()`のUTF-8デコード結果を検証 |
| `AsteriskJavaTest_194.java` | 📝+🔄 混合 | ソース検査（`catch NumberFormatException`）+ 動的テスト（`parsePayloadType`） |
| **calligraphy** | | |
| `CalligraphyTest_1.java` | 🔄 動的 | `applyFont()`、`pullFontPath*()`の実行結果を検証 |
| `CalligraphyTest_2.java` | 🔄 動的 | 同上 |
| **cego** | | |
| `CegoTest_1.java` | 🔄 動的 | `openBitmap()`のIntent・MIMEタイプを検証 |
| **gnucrasha** | | |
| `GnucrashaTest_1a.java` | 🔄 動的 | `executeOnResume()`、`executeOnPause()`の実行結果を検証 |
| `GnucrashaTest_1b.java` | 🔄 動的 | `submitPasscode()`、`pressBack()`の実行結果を検証 |
| **hoverruan_weiboclient4j** | | |
| `HoverruanWeiboclient4jTest_128.java` | 🔄 動的 | Driver経由で`cid(String)`、`cid(long)`の実行結果を検証 |
| **ivantrendafilov_confucius** | | |
| `Ivantrendafilov_confuciusTest_93.java` | 🔄 動的 | Driver経由で`getByteValue(String)`の実行結果を検証 |
| `Ivantrendafilov_confuciusTest_94.java` | 🔄 動的 | Driver経由で`getByteValue(String, byte)`の実行結果を検証 |
| `Ivantrendafilov_confuciusTest_95.java` | 🔄 動的 | Driver経由で`getByteList(String, String)`の実行結果を検証 |
| `Ivantrendafilov_confuciusTest_96.java` | 🔄 動的 | Driver経由で`getLongValue(String)`の実行結果を検証 |
| `Ivantrendafilov_confuciusTest_97.java` | 🔄 動的 | Driver経由で`getLongValue(String, long)`の実行結果を検証 |
| `Ivantrendafilov_confuciusTest_98.java` | 🔄 動的 | Driver経由で`getLongList(String, String)`の実行結果を検証 |
| `Ivantrendafilov_confuciusTest_99.java` | 🔄 動的 | Driver経由で`getShortValue(String)`の実行結果を検証 |
| `Ivantrendafilov_confuciusTest_100.java` | 🔄 動的 | Driver経由で`getShortValue(String, short)`の実行結果を検証 |
| `Ivantrendafilov_confuciusTest_101.java` | 🔄 動的 | Driver経由で`getShortList(String, String)`の実行結果を検証 |
| **jmrtd** | | |
| `JmrtdTest_1.java` | 📝 静的 | `dataOut.close()`パターンを検査（Driver経由） |
| `JmrtdTest_2.java` | 📝 静的 | `Cipher.DECRYPT_MODE`パターンを検査（Driver経由） |
| **jriecken_gae_java_mini_profiler** | | |
| `Jriecken_gae_java_mini_profilerTest_39.java` | 🔄 動的 | `handlesInvalidIdGracefully()`の実行結果を検証 |
| **lnreadera** | | |
| `LnreaderaTest_1.java` | 🔄 動的 | `executeOnDestroyAndCheckSuperCalled()`で検証 |
| `LnreaderaTest_2.java` | 🔄 動的 | 同上 |
| **logblock_logblock_2** | | |
| `LogblockLogblock2Test_15.java` | 🔄 動的 | Driver経由で`paintingTest()`、`writeBlobToBytes()`、`readBlobFromBytes()`の実行結果を検証 |
| **mqtt** | | |
| `MqttTest_389.java` | 🔄 動的 | Driver経由で`getPayload()`、`getHeader()`、`getType()`等の実行結果を検証 |
| **onosendai** | | |
| `OnosendaiTest_1.java` | 📝 静的 | `BatteryHelper.level(context.getApplicationContext())`パターンを検査 |
| **openaiab** | | |
| `OpenaiabTest_1.java` | 🔄 動的 | `onDestroy()`実行後に`wasUnityOnDestroyCalled()`で検証 |
| **pawotag** | | |
| `PawotagTest_1.java` | 📝+🔄 混合 | 動的（encrypt/decrypt往復テスト）+ 静的（`hasEmptyArrayCheck()`） |
| **rhino** | | |
| `RhinoTest_1.java` | 📝 静的 | `Files.readString()`でParser.javaを読み込み、`nf.initFunction(`パターンの出現回数を検査 |

---

## 集計

| 種類 | 件数 | 割合 |
|---|:---:|:---:|
| 📝 **静的テスト** | 12件 | 約33% |
| 🔄 **動的テスト** | 22件 | 約61% |
| 📝+🔄 **混合** | 2件 | 約6% |
| **合計** | 36件 | 100% |

---