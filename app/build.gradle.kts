plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.wafflestudio.snugo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.wafflestudio.snugo"
        minSdk = 29
        targetSdk = 34
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
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.compose.animation:animation:1.7.0-alpha02")
    implementation("androidx.compose.foundation:foundation:1.7.0-alpha02")
    implementation("androidx.compose.runtime:runtime:1.7.0-alpha02")
    implementation("androidx.compose.ui:ui:1.7.0-alpha02")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("com.google.android.gms:play-services-location:21.1.0")

    implementation("androidx.compose.runtime:runtime-livedata:1.6.1")
    implementation("androidx.lifecycle:lifecycle-service:2.7.0")

    // naver map
    implementation("com.naver.maps:map-sdk:3.17.0")
    implementation("io.github.fornewid:naver-map-compose:1.4.1")
    implementation("io.github.fornewid:naver-map-location:21.0.1")

    // testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")
}

kapt {
    correctErrorTypes = true
}
