import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "io.github.dingyi222666"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }

    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(compose.material3)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.materialIconsExtended)

                implementation("com.fifesoft:rsyntaxtextarea:${extra["rsyntaxtextarea.version"]}")
                implementation("org.json:json:${extra["json.version"]}")
                implementation("net.java.dev.jna:jna:${extra["jna.version"]}")
                implementation("net.java.dev.jna:jna-platform:${extra["jna.version"]}")
                implementation("cafe.adriel.bonsai:bonsai-core:${extra["bonsai.version"]}")
                implementation("cafe.adriel.bonsai:bonsai-file-system:${extra["bonsai.version"]}")
                implementation("com.mayakapps.compose:window-styler:${extra["window-styler.version"]}")
            }
        }
        val jvmTest by getting
    }


}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        // ...
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
}

compose.desktop {
    application {
        mainClass = "io.github.dingyi222666.androlua.MainKt"
        nativeDistributions {
            modules("java.instrument", "jdk.unsupported")
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "Androlua-Desktop"
            packageVersion = "1.0.0"
            description = "AndroLua Desktop"
        }
        buildTypes.release.proguard {
            obfuscate.set(true)
        }
    }
}
