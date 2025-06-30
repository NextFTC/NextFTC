plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.nextftc.publishing)
}

allprojects {
    version = property("version") as String
    group = "dev.nextftc"
}