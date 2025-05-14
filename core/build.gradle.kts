plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    androidTarget()
    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(projects.feature.game.domain)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.lottie.compose)
                implementation("com.github.LottieFiles:dotlottie-android:0.5.0")

                implementation(libs.androidx.ui.tooling)
                implementation(libs.compose.ui.tooling.preview)


            }
        }
        val desktopMain by getting
    }
}

android {
    namespace = "com.example.dino.core"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].res.srcDirs("src/androidMain/res")

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
}