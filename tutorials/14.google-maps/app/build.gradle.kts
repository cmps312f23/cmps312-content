plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.9.10"
}

android {
    namespace = "google.maps"
    compileSdk = 34

    defaultConfig {
        applicationId = "google.maps"
        minSdk = 33
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.09.02"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.09.02"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.1")

    val composeVersion = "1.5.4"
    // More material icons
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")

    val navVersion = "2.7.5"
    implementation("androidx.navigation:navigation-compose:$navVersion")

    // Required to be able to use collectAsStateWithLifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    val mapsComposeVersion = "4.3.0"
    implementation("com.google.maps.android:maps-compose:$mapsComposeVersion")

    // Optionally, you can include the Compose utils library for Clustering,
    // Street View metadata checks, etc.
    implementation("com.google.maps.android:maps-compose-utils:$mapsComposeVersion")

    // Optionally, you can include the widgets library for ScaleBar, etc.
    implementation("com.google.maps.android:maps-compose-widgets:$mapsComposeVersion")

    implementation("com.google.android.gms:play-services-location:21.0.1")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.0-RC")

    // Used to display images from Web Urls
    implementation("io.coil-kt:coil-compose:2.5.0")
}