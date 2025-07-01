plugins {
    kotlin("jvm")
    id("java-library")
}

description = "The base for NextFTC, a user-friendly library for FTC. Includes commands, components, and subsystems."

dependencies {
    api(libs.nextftc.bindings)
    api(libs.nextftc.control)
}

nextFTCPublishing {
    displayName = "NextFTC Core"
    logoPath = "../assets/logo-icon.svg"
}

kotlin {
    jvmToolchain(8)
    compilerOptions {
        freeCompilerArgs.add("-Xjvm-default=all")
    }
}