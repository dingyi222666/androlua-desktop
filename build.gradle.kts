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
                // https://mvnrepository.com/artifact/net.java.dev.jna/jna
                implementation("net.java.dev.jna:jna:${extra["jna.version"]}")
                implementation("net.java.dev.jna:jna-platform:${extra["jna.version"]}")

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
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "androlua"
            packageVersion = "1.0.0"
        }
    }
}