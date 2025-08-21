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
            license("GNU General Public License, version 3", "https://www.gnu.org/licenses/gpl-3.0.html")
            developer("Davis Luxenberg", "davis.luxenberg@outlook.com", url = "https://github.com/BeepBot99")
            developer("Rowan McAlpin", "rowan@nextftc.dev", url = "https://rowanmcalpin.com")
            developer("Zach Harel", "ftc@zharel.me", url = "https://github.com/zachwaffle4")
        }
    }
}