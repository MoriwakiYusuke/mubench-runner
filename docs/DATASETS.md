# データセット

## 概要

`datasets/` には MuBench 由来のメタデータが格納されています。出典から一切変更していません。

MuBench から元プロジェクトのソースコードにアクセス可能であったもののみを選定しています。

## 収録プロジェクト

全 30 プロジェクト：

- adempiere
- alibaba-druid
- android-rcs-rcsjta
- androiduil
- apache-gora
- asterisk-java
- calligraphy
- cego
- gnucrasha
- hoverruan-weiboclient4j
- ivantrendafilov-confucius
- jfreechart
- jmrtd
- jodatime
- jriecken-gae-java-mini-profiler
- lnreadera
- logblock-logblock-2
- mqtt
- onosendai
- openaiab
- pawotag
- rhino
- screen-notifications
- tap-apps
- tbuktu-ntru
- testng
- thomas-s-b-visualee
- tucanmobile
- ushahidia
- wordpressa

## ケースディレクトリ構成

```
datasets/<project>/<case>/
├── misuse.yml     # バグ情報
├── original.java  # 元コード
├── misuse.java    # バグを含むコード
└── fix.java       # 正解修正
```

## misuse.yml フォーマット

```yaml
api:
- java.lang.Short
violations:
- missing/exception_handling
crash: true
description: |
  説明文
fix:
  commit: https://github.com/.../commit/xxx
  revision: xxx
location:
  file: org/example/Config.java
  method: getValue(String)
report: https://github.com/.../issues/xxx
source:
  name: Owolabi's Dataset ASE'16
  url: fsl.cs.illinois.edu/spec-eval/
```
