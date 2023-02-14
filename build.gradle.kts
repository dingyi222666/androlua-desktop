import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension


plugins {
    kotlin("multiplatform") apply false
    id("org.jetbrains.compose") apply false
}


subprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            // ...
            freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        }
    }

    plugins.withId("org.jetbrains.kotlin.multiplatform") {
        configure<KotlinMultiplatformExtension> {
            jvm {
                jvmToolchain(11)
                withJava()
            }
        }
    }

}
