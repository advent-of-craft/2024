import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "1.9.21"
    java
}

repositories {
    mavenCentral()
}

dependencies {
    val kotestVersion = "5.8.0"
    val cucumberVersion = "6.10.4"
    val assertJVersion = "3.18.1"

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-junit:$cucumberVersion")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation(kotlin("test"))

    testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)

    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}