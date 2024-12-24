import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "1.9.21"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    val arrowKtVersion = "1.1.3"
    implementation("io.arrow-kt:arrow-core:$arrowKtVersion")

    val kotestVersion = "5.8.0"
    val fakerVersion = "1.0.2"
    val kotestArrowVersion = "1.4.0"

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-assertions-arrow:$kotestArrowVersion")
    testImplementation("com.github.javafaker:javafaker:$fakerVersion")
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