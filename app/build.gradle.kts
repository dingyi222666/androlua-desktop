import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

group = "io.github.dingyi222666"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(17)

    jvm()
    
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(compose.material3)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.materialIconsExtended)

                implementation(libs.rsyntaxtextarea)
                implementation(libs.json)
                implementation(libs.jna)
                implementation(libs.jna.platform)
                implementation(libs.bonsai)
                implementation(libs.bonsai.fileSystem)
                implementation(libs.window.styler)
            }
        }
        val jvmTest by getting
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

composeCompiler {}