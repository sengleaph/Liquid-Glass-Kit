plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.fuu.liquidglass.theme"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    api(project(":liquidglass-core"))

    implementation(platform(libs.androidx.compose.bom))
    api(libs.androidx.runtime)
    api(libs.androidx.ui)
    api(libs.androidx.foundation)
    api(libs.androidx.material3)
    api(libs.liquid)
}
