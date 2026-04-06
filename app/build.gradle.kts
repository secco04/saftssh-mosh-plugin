plugins {
    id("com.android.application")
    // Note: org.jetbrains.kotlin.android is NOT applied here — AGP 9.0+ has built-in
    // Kotlin support and throws an error if the standalone Kotlin plugin is also present.
}

android {
    namespace  = "de.lobianco.saftssh.mosh"
    compileSdk = 36

    defaultConfig {
        applicationId   = "de.lobianco.saftssh.mosh"
        minSdk          = 26
        targetSdk       = 36
        versionCode     = 12
        versionName     = "1.0.12"

        // Only arm64 atm
        ndk { abiFilters += listOf("arm64-v8a") }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packaging {
        jniLibs {
            useLegacyPackaging = true
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.18.0")
    implementation("androidx.browser:browser:1.10.0")
}
