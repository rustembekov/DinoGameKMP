plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    androidTarget()
    jvm("desktop")

    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.lifecycle.viewmodel.ktx)
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(libs.koin.core)
                implementation(projects.feature.game.domain)
                implementation(projects.feature.game.data)
                implementation(compose.runtime)

            }
        }
        val desktopMain by getting
    }
}
android {
    namespace = "com.example.dino"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}