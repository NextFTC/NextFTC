plugins {
    kotlin("jvm")
    id("java-library")
    alias(libs.plugins.kotlinx.serialization)
}

description = "The base for NextFTC, a user-friendly library for FTC. Includes commands, components, and subsystems."

dependencies {
    api(libs.nextftc.bindings)
    api(libs.nextftc.control)
    implementation(libs.kotlinx.serialization.json)
}

nextFTCPublishing {
    displayName = "NextFTC Core"
    logoPath = "../assets/logo-icon.svg"
}

kotlin {
    jvmToolchain(8)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjvm-default=all", "-Xconsistent-data-class-copy-visibility")
    }
}