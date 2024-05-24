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
include(":app")
include(":core:view")
include(":feature:welcome")
include(":feature:signup")
include(":feature:login")
include(":feature:timeline")
include(":feature:postdetails")
include(":domain:auth")
include(":core:network")
include(":core:database")
