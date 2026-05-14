plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Plugin de Firebase App Distribution para distribución automática
    id("com.google.firebase.appdistribution")
    // Plugin de JaCoCo para reportes de cobertura de código
    jacoco
}

android {
    namespace = "com.example.cicdapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cicdapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // Configuración de firma para release
    // Lee credenciales desde variables de entorno que GitHub Actions inyecta desde secrets
    signingConfigs {
        create("release") {
            storeFile = file(System.getenv("KEYSTORE_PATH") ?: "release-key.jks")
            storePassword = System.getenv("KEYSTORE_PASS") ?: ""
            keyAlias = System.getenv("KEY_ALIAS") ?: ""
            keyPassword = System.getenv("KEY_PASS") ?: ""
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Usa la configuración de firma release para generar APKs firmados
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }
}

// Configuración de Firebase App Distribution
// Define las notas de release y los testers que recibirán los builds
firebaseAppDistribution {
    releaseNotes = "Build automático desde CI/CD"
    testers = "qa@equipo.com"
}

// Configuración de JaCoCo para quality gate de cobertura
jacoco {
    toolVersion = "0.8.11"
}

// Finaliza la ejecución de tests con la generación del reporte de JaCoCo
tasks.withType<Test> {
    finalizedBy(tasks.named("jacocoTestReport"))
}

// Tarea para generar reporte de cobertura en formato HTML y XML
tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.named("testDebugUnitTest"))

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }

    val fileFilter = listOf(
        "**/R.class", "**/R$*.class",
        "**/BuildConfig.*", "**/Manifest*.*",
        "**/*Test*.*", "**/*\$*.*"
    )

    val debugTree = fileTree("${layout.buildDirectory.get().asFile}/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }

    val mainSrc = "${project.projectDir}/src/main/java"

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(fileTree(layout.buildDirectory.get().asFile) {
        include("jacoco/testDebugUnitTest.exec")
    })
}

// Quality gate: verifica que la cobertura de líneas sea >= 60%
tasks.register("jacocoCoverageVerification") {
    dependsOn("jacocoTestReport")
    doLast {
        val reportFile = file("${layout.buildDirectory.get().asFile}/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")
        if (reportFile.exists()) {
            val text = reportFile.readText()
            // Parsear contadores de línea del reporte XML de JaCoCo
            val lineRegex = Regex("""<counter type="LINE" missed="(\d+)" covered="(\d+)"/>""")
            val matches = lineRegex.findAll(text).toList()
            if (matches.isNotEmpty()) {
                val lastMatch = matches.last()
                val missed = lastMatch.groupValues[1].toDouble()
                val covered = lastMatch.groupValues[2].toDouble()
                val total = missed + covered
                val coverage = if (total > 0) (covered / total) * 100 else 0.0
                println("Cobertura de líneas: %.2f%%".format(coverage))
                // Umbral mínimo de cobertura: 60%
                if (coverage < 60.0) {
                    throw GradleException(
                        "Quality Gate FALLIDO: cobertura de líneas %.2f%% está por debajo del umbral del 60%%".format(coverage)
                    )
                }
                println("Quality Gate APROBADO: cobertura >= 60%")
            }
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Dependencias de testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
