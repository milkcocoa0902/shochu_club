buildscript {
    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.5")
    }
}

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
//    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false

    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.daggerHilt.android) apply false
    alias(libs.plugins.jetbrains.kotlin.serialization) apply false
    alias(libs.plugins.googleKsp) apply false
    alias(libs.plugins.aboutLibraries) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}
