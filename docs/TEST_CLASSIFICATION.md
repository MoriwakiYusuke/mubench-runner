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
| `AdempiereTest_1.java` | 🔄 動的 | `encrypt()`→`decrypt()`ラウンドトリップで非ASCII文字（日本語・中国語・絵文字）を検証 |
| `AdempiereTest_2.java` | 🔄 動的 | 同上 |
| **alibaba_druid** | | |
| `AlibabaDruidTest_1.java` | 📝 静的 | `cipher = Cipher.getInstance`パターンを検査 |
| `AlibabaDruidTest_2.java` | 📝 静的 | `catch (InvalidKeyException`パターンを検査 |
| **android_rcs_rcsjta** | | |
| `AndroidRcsRcsjtaTest_1.java` | 🔄 動的 | `getContributionId()`の結果一貫性を検証、非ASCII文字でも正常動作を確認 |
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
| `JmrtdTest_2.java` | 📝+🔄 混合 | ソース検査（`Cipher.DECRYPT_MODE`）+ 動的RSA暗号化/復号化ラウンドトリップ検証 |
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
| `RhinoTest_1.java` | 📝+🔄 混合 | 動的（`parse()`実行でタイムアウト検出）+ 静的（`initFunction`呼び出し回数検査） |
| **screen_notifications** | | |
| `ScreenNotificationsTest_1.java` | 🔄 動的 | Driver経由で`loadInBackground()`を実行し、OOMスロー時のハンドリングを検証 |
| **tap_apps** | | |
| `TapAppsTest_1.java` | 📝+🔄 混合 | 動的（encrypt/decrypt往復テスト）+ 静的（`getCipherTransformationFromSource()`でソース解析） |
| **tbuktu_ntru** | | |
| `Tbuktu_ntruTest_473.java` | 🔄 動的 | `writeTo()`出力検証、`writeToBuffered()`でflush動作を動的検証 |
| `Tbuktu_ntruTest_474.java` | 🔄 動的 | `writeTo()`出力検証、`writeToBuffered()`でflush動作を動的検証 |
| `Tbuktu_ntruTest_475.java` | 📝+🔄 混合 | 動的（`getEncoded()`出力検証）+ 静的（`hasFlushOrCloseInGetEncoded()`でソース解析） |
| `Tbuktu_ntruTest_476.java` | 📝+🔄 混合 | 動的（`getEncoded()`出力検証）+ 静的（`hasFlushOrCloseInGetEncoded()`でソース解析） |
| **testng** | | |
| `TestngTest_16.java` | 📝+🔄 混合 | 静的（`hasSynchronizedBlock()`, `isCorrectlyFixed()`でソース解析）+ 動的（`testPanelInitialization()`, `testGetContentWithMockSuite()`, `testConcurrentAccess()`） |
| `TestngTest_17.java` | 📝+🔄 混合 | 静的（`hasSynchronizedBlock()`, `isCorrectlyFixed()`でソース解析）+ 動的（`testReporterInitialization()`, `testGenerateReportWithMockContext()`, `testOnStartWithMockContext()`） |
| `TestngTest_18.java` | 📝+🔄 混合 | 静的（`hasSynchronizedBlock()`, `isCorrectlyFixed()`でソース解析）+ 動的（`testReporterInitialization()`, `testGenerateReportWithMockContext()`, `testOnFinishWithMockContext()`） |
| `TestngTest_21.java` | 📝+🔄 混合 | 静的（`hasSynchronizedBlock()`, `isCorrectlyFixed()`でソース解析）+ 動的（`testModelInitialization()`, `testGetSuites()`, `testGetAllFailedResults()`） |
| `TestngTest_22.java` | 📝+🔄 混合 | 静的（`hasSynchronizedBlock()`, `isCorrectlyFixed()`でソース解析）+ 動的（`testReporterInitialization()`, `testGenerateReportWithMockSuite()`, `testMockSuiteAttributes()`） |
| **thomas_s_b_visualee** | | |
| `Thomas_s_b_visualeeTest_29.java` | 🔄 動的 | Driver経由で`findAndSetPackage(JavaSource)`の実行結果を検証 |
| `Thomas_s_b_visualeeTest_30.java` | 🔄 動的 | Driver経由で`jumpOverJavaToken(String, Scanner)`の実行結果を検証 |
| `Thomas_s_b_visualeeTest_32.java` | 🔄 動的 | Driver経由で`scanAfterClosedParenthesis(String, Scanner)`の実行結果を検証 |
| **tucanmobile** | | |
| `TucanmobileTest_1.java` | 🔄 動的 | Driver経由で`onPreExecute()`、`onPostExecute()`を実行し、`dialog.isShowing()`チェック有無を検証 |
| **ushahidia** | | |
| `UshahidiaTest_1.java` | 🔄 動的 | Driver経由で`getReportState()`を実行し、`Cursor.isClosed()`でリソース解放を検証 |
| **wordpressa** | | |
| `WordpressaTest_1.java` | 🔄 動的 | Driver経由で`restoreListScrollPosition()`を実行し、`isAdded()`チェック有無を検証 |
| `WordpressaTest_3.java` | 🔄 動的 | Driver経由で`onClick(R.id.more)`を実行し、`mSelectionEnd > str.length()`のbounds check有無を検証 |

---

## 集計

| 種類 | 件数 | 割合 |
|---|:---:|:---:|
| 📝 **静的テスト** | 8件 | 約15% |
| 🔄 **動的テスト** | 35件 | 約65% |
| 📝+🔄 **混合** | 11件 | 約20% |
| **合計** | 54件 | 100% |

---