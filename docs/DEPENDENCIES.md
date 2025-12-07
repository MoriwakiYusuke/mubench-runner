# libs - ローカルJAR依存

このディレクトリには、データセットの永続性と再現性を保証するためのJAR依存が含まれています。

## 目的

- **永続性**: Maven Centralのリポジトリ消失やバージョン削除に対する耐性
- **再現性**: 将来的にも同一の依存でビルド・テスト可能
- **オフライン対応**: ネットワーク接続なしでもビルド可能

## 含まれるJAR

### プロジェクト依存（ローカル参照）

| ファイル名 | バージョン | 用途 |
|-----------|-----------|------|
| android-4.1.1.4.jar | 4.1.1.4 | Androidプロジェクト（スタブ） |
| aopalliance-1.0.jar | 1.0 | testng（Guice依存） |
| asterisk-java-3.41.0.jar | 3.41.0 | asterisk-javaプロジェクト |
| bcprov-jdk18on-1.77.jar | 1.77 | jmrtd（BouncyCastle暗号） |
| commons-codec-1.16.0.jar | 1.16.0 | tap-apps（Base64） |
| commons-logging-1.2.jar | 1.2 | pawotag |
| druid-1.2.20.jar | 1.2.20 | alibaba-druid |
| guice-5.1.0.jar | 5.1.0 | testng（DI） |
| hamcrest-core-1.3.jar | 1.3 | JUnit 4依存 |
| jackson-core-asl-1.9.13.jar | 1.9.13 | hoverruan-weiboclient4j |
| jackson-mapper-asl-1.9.13.jar | 1.9.13 | hoverruan-weiboclient4j |
| javax.annotation-api-1.3.2.jar | 1.3.2 | testng（アノテーション） |
| javax.inject-1.jar | 1 | testng（DI） |
| javax.servlet-api-4.0.1.jar | 4.0.1 | pawotag（Servlet API） |
| javax.servlet.jsp-api-2.3.3.jar | 2.3.3 | pawotag（JSP API） |
| jsr305-3.0.2.jar | 3.0.2 | testng（Nullable等アノテーション） |
| junit-4.13.2.jar | 4.13.2 | JUnit 4テスト |
| maven-plugin-api-3.1.0.jar | 3.1.0 | testng（Maven統合） |
| scribe-1.3.7.jar | 1.3.7 | hoverruan-weiboclient4j（OAuth） |
| slf4j-api-2.0.9.jar | 2.0.9 | ivantrendafilov-confucius |
| slf4j-simple-2.0.9.jar | 2.0.9 | ivantrendafilov-confucius |
| testng-6.9.4.jar | 6.9.4 | testngプロジェクト（旧バージョン） |
| testng-7.9.0.jar | 7.9.0 | testngプロジェクト（新バージョン） |

### テストフレームワーク（バックアップ用、現在はMaven Central参照）

| ファイル名 | バージョン | 用途 |
|-----------|-----------|------|
| junit-jupiter-5.10.2.jar | 5.10.2 | JUnit 5 メインモジュール |
| junit-jupiter-api-5.10.2.jar | 5.10.2 | JUnit 5 API |
| junit-jupiter-engine-5.10.2.jar | 5.10.2 | JUnit 5 エンジン |
| junit-jupiter-params-5.10.2.jar | 5.10.2 | JUnit 5 パラメータ化テスト |
| junit-platform-launcher-1.10.2.jar | 1.10.2 | JUnit Platform ランチャー |
| junit-platform-engine-1.10.2.jar | 1.10.2 | JUnit Platform エンジン |
| junit-platform-commons-1.10.2.jar | 1.10.2 | JUnit Platform 共通 |
| opentest4j-1.3.0.jar | 1.3.0 | テスト例外 |
| apiguardian-api-1.1.2.jar | 1.1.2 | API安定性アノテーション |

### アノテーションプロセッサ（バックアップ用、現在はMaven Central参照）

| ファイル名 | バージョン | 用途 |
|-----------|-----------|------|
| lombok-1.18.30.jar | 1.18.30 | Lombok |
| annotations-24.1.0.jar | 24.1.0 | JetBrains Annotations |

## 取得元

すべてのJARはMaven Central (https://repo1.maven.org/maven2/) から取得しています。

## 使用方法

### 通常使用（推奨）
現在のbuild.gradle.ktsはプロジェクト依存をローカルJARから、テストフレームワークはMaven Centralから取得します。

### 完全オフライン使用
Maven Centralが利用できなくなった場合は、build.gradle.ktsを修正してすべてのJARをローカルから参照するように変更してください。
