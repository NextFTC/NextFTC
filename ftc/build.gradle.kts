plugins {
    kotlin("android")
    id("com.android.library")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "dev.nextftc.ftc"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    publishing {
        singleVariant("release")
    }
}

description =
    "The FTC-dependent library for NextFTC, a user-friendly library for FTC. Includes OpModes and FTC-specific components."

dependencies {
    api(project(":core"))
    compileOnly(libs.bundles.ftc)
    implementation(libs.kotlinx.serialization.json)
}

nextFTCPublishing {
    displayName = "NextFTC FTC"
    logoPath = "../assets/logo-icon.svg"
}