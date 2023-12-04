plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.9.10"
    id ("com.google.devtools.ksp") version "1.9.10-1.0.13"
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "shop.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "shop.app"
        minSdk = 27
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "34.0.0"
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    /*testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")*/
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.1")

    val composeVersion = "1.5.4"
    // More material icons
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")

    val navVersion = "2.7.5"
    implementation("androidx.navigation:navigation-compose:$navVersion")

    // Room
    val roomVersion = "2.6.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    // Kotlin Symbol Processing (KSP) - for processing annotations
    ksp("androidx.room:room-compiler:$roomVersion")
    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")

    // Kotlin Datetime
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")

    // Required to be able to use collectAsStateWithLifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))

    // Declare the dependency for the Cloud Firestore library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    // Add the dependencies for the Firebase Cloud Messaging
    // When using the BoM, you don't specify versions in Firebase library dependencies
    //implementation("com.google.firebase:firebase-messaging")
    //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // Firebase Authentication
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Firebase storage
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0")

    // Used to display images from Web Urls
    implementation("io.coil-kt:coil-compose:2.5.0")
}