@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

val properties = gradleLocalProperties(rootDir, providers)

android {
    namespace = "com.xeniac.oskardemoproject"
    compileSdk = 34
    buildToolsVersion = "34.0.0"

    defaultConfig {
        applicationId = "com.xeniac.oskardemoproject"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        // Keeps language resources for only the locales specified below.
        resourceConfigurations.addAll(listOf("en-rUS"))

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            versionNameSuffix = " - Debug"
            applicationIdSuffix = ".debug"
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    compileOptions {
        // Java 8+ API Desugaring Support
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

hilt {
    enableAggregatingTask = true
}

dependencies {
    // Java 8+ API Desugaring Support
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Jetpack Compose
    val composeBoM = platform("androidx.compose:compose-bom:2024.03.00")
    implementation(composeBoM)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3") // Material Design 3
    implementation("androidx.compose.runtime:runtime-livedata") // Compose Integration with LiveData
    implementation("androidx.activity:activity-compose:1.8.2") // Compose Integration with Activities
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0") // Compose Navigation Integration with Hilt
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1") // Compose Constraint Layout

    // Android Studio Compose Preview Support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    ksp("com.google.dagger:hilt-compiler:2.51.1")

    // Architectural Components
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0") // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0") // ViewModel Utilities for Compose
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0") // Lifecycles Only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0") // Lifecycle Utilities for Compose

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    // Ktor Client Library
    implementation("io.ktor:ktor-client-core:2.3.9")
    implementation("io.ktor:ktor-client-okhttp:2.3.9") // Ktor OkHttp Engine
    implementation("io.ktor:ktor-client-content-negotiation:2.3.9")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.9")
    implementation("io.ktor:ktor-client-logging:2.3.9")

    // Kotlin JSON Serialization Library
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // Preferences DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Timber Library
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Coil Library
    implementation(platform("io.coil-kt:coil-bom:2.6.0"))
    implementation("io.coil-kt:coil-compose")
    implementation("io.coil-kt:coil-svg")
    implementation("io.coil-kt:coil-gif")

    // Ory client Library
    implementation("sh.ory:ory-client:1.6.2")

    // Local Unit Test Libraries
    testImplementation("com.google.truth:truth:1.4.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:2.2.0") // Test Helpers for LiveData
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    testImplementation("androidx.room:room-testing:2.6.1")

    // Instrumentation Test Libraries
    androidTestImplementation("com.google.truth:truth:1.4.2")
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0") // Test Helpers for LiveData
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.51.1")
    kspAndroidTest("com.google.dagger:hilt-compiler:2.51.1")

    // UI Tests
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(composeBoM)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}