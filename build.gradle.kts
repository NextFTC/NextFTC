import io.deepmedia.tools.deployer.DeployerExtension

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

subprojects {
    extensions.configure<DeployerExtension> {
        projectInfo {
            url = "https://nextftc.dev/nextftc"
            scm {
                fromGithub("NextFTC", "NextFTC")
            }
            developer("Davis Luxenberg", "davis.luxenberg@outlook.com", url = "https://github.com/BeepBot99")
            developer("Rowan McAlpin", "rowan@nextftc.dev", url = "https://rowanmcalpin.com")
        }
    }
}