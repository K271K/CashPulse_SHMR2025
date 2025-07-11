enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

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

rootProject.name = "CashPulse"
include(":app")
include(":core")
include(":core:navigation")
include(":core:ui")
include(":feature:expenses")
include(":feature:expenses:ui")
include(":feature:expenses:domain")
include(":feature:expenses:data")
include(":core:network")
include(":feature:incomes:ui")
include(":feature:account:ui")
include(":feature:settings:ui")
include(":feature:category:ui")
include(":feature:incomes:data")
include(":core:domain")
include(":feature:incomes:domain")
include(":core:data")
include(":feature:category:domain")
include(":feature:category:data")
include(":feature:account:data")
include(":feature:account:domain")
