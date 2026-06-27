plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

android {
    namespace = "com.fuu.liquidglass.bundle"
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

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

// Umbrella module: no source of its own — re-exports the three real library modules
// via api() so consumers who depend on just "liquidglass" get core + theme + components.
dependencies {
    api(project(":liquidglass-core"))
    api(project(":liquidglass-theme"))
    api(project(":liquidglass-components"))
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.sengleaph"
                artifactId = project.name                  // -> "liquidglass"
                version = System.getenv("VERSION") ?: "0.1.0"
            }
        }
    }
}
