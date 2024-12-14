import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "1.9.21"
    kotlin("kapt") version "1.6.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    val kotestVersion = "5.8.0"
    val mapStructVersion = "1.5.3.Final"
    implementation("org.mapstruct:mapstruct:$mapStructVersion")
    kapt("org.mapstruct:mapstruct-processor:$mapStructVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation(kotlin("test"))
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

kapt {
    correctErrorTypes = true
}