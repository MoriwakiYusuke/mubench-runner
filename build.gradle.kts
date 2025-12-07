plugins {
    // アプリケーションとして実行するためのプラグイン
    application
}

repositories {
    // ライブラリのダウンロード先 (Maven Central)
    // テストフレームワーク(JUnit5)とアノテーションプロセッサ(Lombok)のみMaven Centralから取得
    mavenCentral()
}

dependencies {
    // テスト用ライブラリ (JUnit 5) - テストフレームワークはMaven Centralから
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // Lombok - アノテーションプロセッサはMaven Centralから
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    compileOnly("org.jetbrains:annotations:24.1.0")
    
    // ==========================================================================
    // ローカルJAR依存 (libs/配下に永続化)
    // データセットの再現性を保証するため、外部依存をリポジトリ内に保存
    // ==========================================================================
    implementation(files("libs/druid-1.2.20.jar"))
    implementation(files("libs/android-4.1.1.4.jar"))
    implementation(files("libs/junit-4.13.2.jar"))
    implementation(files("libs/hamcrest-core-1.3.jar"))
    implementation(files("libs/scribe-1.3.7.jar"))
    implementation(files("libs/jackson-mapper-asl-1.9.13.jar"))
    implementation(files("libs/jackson-core-asl-1.9.13.jar"))
    // ivantrendafilov-confucius用 SLF4J
    implementation(files("libs/slf4j-api-2.0.9.jar"))
    implementation(files("libs/slf4j-simple-2.0.9.jar"))
    // jmrtd用 BouncyCastle (ISO9797Alg3Mac等の暗号プリミティブ)
    implementation(files("libs/bcprov-jdk18on-1.77.jar"))
    // pawotag用 Servlet API, JSP API, Commons Logging
    compileOnly(files("libs/javax.servlet-api-4.0.1.jar"))
    testImplementation(files("libs/javax.servlet-api-4.0.1.jar"))
    compileOnly(files("libs/javax.servlet.jsp-api-2.3.3.jar"))
    implementation(files("libs/commons-logging-1.2.jar"))
    // asterisk-java用 Asterisk-Java library
    implementation(files("libs/asterisk-java-3.41.0.jar"))
    // tap-apps用 Commons Codec (Base64)
    implementation(files("libs/commons-codec-1.16.0.jar"))
    // testng用 Guice, javax.annotation, TestNG (ローカルJARで永続化)
    implementation(files("libs/guice-5.1.0.jar"))
    implementation(files("libs/javax.inject-1.jar"))
    implementation(files("libs/aopalliance-1.0.jar"))
    implementation(files("libs/javax.annotation-api-1.3.2.jar"))
    implementation(files("libs/jsr305-3.0.2.jar"))  // @Nullable等のアノテーション
    implementation(files("libs/testng-6.9.4.jar"))
    // thomas-s-b-visualee用 Maven Plugin API (LogProvider用)
    implementation(files("libs/maven-plugin-api-3.1.0.jar"))
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

// =============================================================================
// バイナリ解析者向けエクスポートタスク
// =============================================================================

// バリアント別JAR出力
listOf("original", "misuse", "fixed").forEach { variant ->
    tasks.register<Jar>("export${variant.replaceFirstChar { it.uppercase() }}") {
        group = "export"
        description = "Export $variant variant classes as JAR"
        archiveBaseName.set("mubench-$variant")
        archiveVersion.set("1.0")
        
        from(sourceSets.main.get().output) {
            include("**/$variant/**/*.class")
        }
        
        destinationDirectory.set(file("$buildDir/exports/variants"))
    }
}

// 全バリアントを一括出力
tasks.register("exportAll") {
    group = "export"
    description = "Export all variant JARs (original, misuse, fixed)"
    dependsOn("exportOriginal", "exportMisuse", "exportFixed")
    doLast {
        println("✓ Exported JARs to build/exports/variants/")
    }
}

// プロジェクト別・バリアント別にクラスファイルをコピー
tasks.register<Copy>("exportClasses") {
    group = "export"
    description = "Export class files organized by project/case/variant"
    dependsOn("compileJava")
    
    from("$buildDir/classes/java/main")
    into("$buildDir/exports/classes")
    
    // フォルダ構造を維持
    includeEmptyDirs = false
    include("**/*.class")
    
    doLast {
        println("✓ Exported class files to build/exports/classes/")
    }
}

// ペア比較用: プロジェクト/ケース単位でまとめる
tasks.register("exportPairs") {
    group = "export"
    description = "Export class files as comparison pairs (original vs misuse vs fixed)"
    dependsOn("compileJava")
    
    doLast {
        val mainClassesDir = file("$buildDir/classes/java/main")
        val pairsDir = file("$buildDir/exports/pairs")
        pairsDir.mkdirs()
        
        // プロジェクトディレクトリを走査
        mainClassesDir.listFiles()?.filter { it.isDirectory }?.forEach { projectDir ->
            projectDir.listFiles()?.filter { it.isDirectory && it.name.startsWith("_") }?.forEach { caseDir ->
                val caseName = "${projectDir.name}${caseDir.name}"
                val pairDir = File(pairsDir, caseName)
                
                listOf("original", "misuse", "fixed").forEach { variant ->
                    val variantDir = File(caseDir, variant)
                    if (variantDir.exists()) {
                        val targetDir = File(pairDir, variant)
                        targetDir.mkdirs()
                        variantDir.copyRecursively(targetDir, overwrite = true)
                    }
                }
            }
        }
        println("✓ Exported pairs to build/exports/pairs/")
    }
}

// 全エクスポートタスク
tasks.register("exportBinaries") {
    group = "export"
    description = "Run all export tasks for binary analysis"
    dependsOn("exportAll", "exportClasses", "exportPairs")
    doLast {
        println("""
            |
            |=== Export Complete ===
            |  build/exports/
            |  ├── variants/     - Variant JARs (mubench-original.jar, etc.)
            |  ├── classes/      - Raw class files by project
            |  └── pairs/        - Comparison pairs by case
            |
        """.trimMargin())
    }
}