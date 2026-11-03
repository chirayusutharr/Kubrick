import java.util.Properties
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.kubrick"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.kubrick"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Read TMDb token from local.properties
        val localProperties = Properties()
        val localFile = rootProject.file("local.properties")

        if (localFile.exists()) {
            localFile.inputStream().use { localProperties.load(it) }
        }

        val tmdbToken = localProperties.getProperty("TMDB_ACCESS_TOKEN") ?: ""
        buildConfigField("String", "TMDB_ACCESS_TOKEN", "\"$tmdbToken\"")
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)


//    ackdrop by Kyant,
    implementation("io.github.kyant0:backdrop:2.0.1")
//    nav
    implementation("androidx.navigation:navigation-compose:2.9.6")
    // Material icons
    implementation("androidx.compose.material:material-icons-extended")
    // Retrofit + Gson
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // Coil for movie posters
    implementation("io.coil-kt:coil-compose:2.7.0")

   //   Unresolved reference 'compose'.
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.2")

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
