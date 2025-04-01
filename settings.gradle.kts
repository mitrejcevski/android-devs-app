pluginManagement {
    repositories {
        google()
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

rootProject.name = "AndroidDevs"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":feature:welcome")
include(":feature:signup")
include(":feature:login")
include(":feature:timeline")
include(":feature:postdetails")
include(":shared:ui")
include(":shared:network")
include(":shared:database")
include(":domain:auth")
include(":testutils")
