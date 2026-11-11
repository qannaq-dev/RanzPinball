plugins {
    id("com.android.application")
}

// The signing key lives in GitHub secrets (KEYSTORE_B64 / KEYSTORE_PASSWORD), never in the repo.
// The build workflow decodes it to app/release.keystore before building.
val ksFile = file("release.keystore")
val ksPassword: String? = System.getenv("KEYSTORE_PASSWORD")

android {
    namespace = "com.ranzpinball"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ranzpinball"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.00"
    }

    signingConfigs {
        if (ksFile.exists() && ksPassword != null) {
            create("release") {
                storeFile = ksFile
                storePassword = ksPassword
                keyAlias = "ranzpinball"
                keyPassword = ksPassword
            }
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.findByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
