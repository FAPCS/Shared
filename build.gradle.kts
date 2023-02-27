@file:Suppress("UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.10"

    id("maven-publish")
}

group = "me.fapcs"
version = "0.1.0"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        jvmToolchain(16)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("org.apache.logging.log4j:log4j-api:2.19.0")
                implementation("org.apache.logging.log4j:log4j-core:2.19.0")
            }
        }
        val jvmTest by getting

        val jsMain by getting
        val jsTest by getting
    }
}
