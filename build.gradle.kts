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
    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    compileOnly("org.jetbrains:annotations:24.1.0")
    implementation("com.alibaba:druid:1.2.20")
    implementation("com.google.android:android:4.1.1.4")
    implementation("junit:junit:4.13.2")
    implementation("org.scribe:scribe:1.3.7")
    implementation("org.codehaus.jackson:jackson-mapper-asl:1.9.13")
    // ivantrendafilov-confucius用 SLF4J
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    // jmrtd用 BouncyCastle (ISO9797Alg3Mac等の暗号プリミティブ)
    implementation("org.bouncycastle:bcprov-jdk18on:1.77")
    // pawotag用 Servlet API, JSP API, Commons Logging
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
    testImplementation("javax.servlet:javax.servlet-api:4.0.1")
    compileOnly("javax.servlet.jsp:javax.servlet.jsp-api:2.3.3")
    implementation("commons-logging:commons-logging:1.2")
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