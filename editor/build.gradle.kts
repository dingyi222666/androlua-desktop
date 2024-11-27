import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    java
    kotlin("multiplatform")
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

group = "io.github.dingyi222666.compose"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(17)

    jvm()

    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(compose.material3)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.materialIconsExtended)

            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(compose.desktop.currentOs)
                implementation(compose.material3)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.materialIconsExtended)
                implementation(compose.uiTestJUnit4)
            }
        }
    }
}



