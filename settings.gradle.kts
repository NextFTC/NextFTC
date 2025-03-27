pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // For PedroPathing:
        maven(url = "https://maven.pedropathing.com/" )
        // For Dashboard:
        maven(url = "https://maven.brott.dev/" )
        // For NextControl:
        maven(url = "https://maven.rowanmcalpin.com/")
    }
}

rootProject.name = "NextFTC"
include(":core")
include(":ftc")
include(":pedro")
include(":hardware")
