plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    namespace = "dev.nextftc.hardware"
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

dependencies {
    implementation(project(":core"))
    implementation(project(":ftc"))
    compileOnly(libs.bundles.ftc)
}

description =
    "The hardware library for NextFTC, a user-friendly library for FTC. Includes hardware interfaces, wrapper implementations, and hardware commands."

nextFTCPublishing {
    displayName = "NextFTC Hardware"
    logoPath = "../assets/logo-icon.svg"
}