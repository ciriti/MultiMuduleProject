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
    }
}

rootProject.name = "PostTemplate"
include(":app")

include(":core:ui")
include(":core:util")
include(":core:data")
include(":core:data:network")
include(":core:data:database")

include(":feature:auth")
include(":feature:posts")
include(":feature:profile")
include(":feature:downloader")

include(":sdk")
include(":sdk-messagequeue")
include(":permission-handler")
