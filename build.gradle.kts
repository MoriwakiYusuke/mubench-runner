plugins {
    // アプリケーションとして実行するためのプラグイン
    application
}

repositories {
    // ライブラリのダウンロード先 (Maven Central)
    mavenCentral()
}

dependencies {
    // テスト用ライブラリ (JUnit 5)
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("com.alibaba:druid:1.2.20")
    implementation ("com.google.android:android:4.1.1.4")
    implementation ("junit:junit:4.13.2")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
        //languageVersion.set(JavaLanguageVersion.of(25))
    }
}

application {
    // メインクラスの指定 (applicationプラグインで必須のため仮設定)
    // 実際は ./gradlew test でテスト実行するのがメインなので、ここは適当でOK
    mainClass.set("adempiere.original.Secure")
}

tasks.named<Test>("test") {
    // JUnit 5 を有効化
    useJUnitPlatform()

    // テスト実行時に結果(Pass/Fail)や標準出力をコンソールに表示する設定
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

// ソースコードのエンコーディングをUTF-8に固定 (文字化け防止)
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}