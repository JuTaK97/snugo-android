// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    id("com.google.dagger.hilt.android") version "2.48" apply false
}
