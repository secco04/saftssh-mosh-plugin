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
        versionCode     = 11
        versionName     = "1.0.11"

        // Only arm64 — same constraint as main SaftSSH app
        ndk { abiFilters += listOf("arm64-v8a") }
    }

    buildTypes {
        release {
            isMinifyEnabled = true   // nothing to obfuscate in a pure binary-delivery plugin
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packaging {
        jniLibs {
            // Extract libmosh-client.so to nativeLibraryDir at install time so it
            // sits on an exec-mounted filesystem.  The main SaftSSH app reads the path
            // via PackageManager.getApplicationInfo().nativeLibraryDir and execs it
            // directly — no copy into noexec storage needed.
            useLegacyPackaging = true
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.15.0")
}
