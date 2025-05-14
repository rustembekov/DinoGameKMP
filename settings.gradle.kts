enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

rootProject.name = "DinoGameKMP"

include(":composeApp")

include(":core")

include(":shared")

include(":feature:game:domain")
include(":feature:game:data")
include(":feature:game:presentation")

include(":feature:highscore:domain")
include(":feature:highscore:data")
