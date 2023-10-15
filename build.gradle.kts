import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.4.21"
    id("org.jetbrains.compose")
}

group = "com.doyoonkim"
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
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting
    }
}

// Top-level dependencies setting
dependencies {
    commonMainImplementation("io.ktor:ktor-client-core:2.3.5")
    commonMainImplementation("io.ktor:ktor-client-cio:2.3.5")
    commonMainImplementation("io.ktor:ktor-client-content-negotiation:2.3.5")
    commonMainImplementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Unsplash Explorer"
            packageVersion = "1.0.0"
        }
    }
}
