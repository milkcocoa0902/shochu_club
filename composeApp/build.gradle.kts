import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
//    alias(libs.plugins.compose.compiler)

    alias(libs.plugins.jetbrains.kotlin.serialization)

    id("com.google.dagger.hilt.android")
    alias(libs.plugins.googleKsp)
    id("com.mikepenz.aboutlibraries.plugin")
    alias(libs.plugins.kotlinx.rpc.platform)
    id("com.google.gms.google-services")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
        // K2はこちら
//        @OptIn(ExperimentalKotlinGradlePluginApi::class)
//        compilerOptions {
//            jvmTarget.set(JvmTarget.JVM_11)
//        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(projects.shared)
            implementation(libs.ktor.client.cio)
            implementation(libs.kotlinx.rpc.krpc.client)
            implementation(libs.kotlinx.rpc.krpc.ktor.client)
            implementation(libs.kotlinx.rpc.krpc.serialization.protobuf)
        }
    }
}

android {
    namespace = "com.milkcocoa.info.shochu_club"
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    signingConfigs {
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        println(properties)
        create("dev") {
            keyAlias = properties["devKey.alias"] as String
            storeFile = File(properties["devKey.file"] as String)
            storePassword = properties["devKey.storePassword"] as String
            keyPassword = properties["devKey.keyPassword"] as String
        }
        create("prod") {
            keyAlias = properties["prodKey.alias"] as String
            storeFile = File(properties["prodKey.file"] as String)
            storePassword = properties["prodKey.storePassword"] as String
            keyPassword = properties["prodKey.keyPassword"] as String
        }
    }

    defaultConfig {
        applicationId = "com.milkcocoa.info.shochu_club"
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
        targetSdk =
            libs.versions.android.targetSdk
                .get()
                .toInt()
        versionCode = 1
        versionName = "1.0"
        signingConfig = signingConfigs.getByName("dev")
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("dev")
            applicationIdSuffix = ".dev"
        }

        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("prod")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        implementation(libs.hilt.android)
        ksp(libs.hilt.android.compiler)
        implementation(libs.aboutlibraries.core)
        implementation(libs.aboutlibraries)
        implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
        implementation("com.google.firebase:firebase-analytics")
        debugImplementation(compose.uiTooling)
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
}
dependencies {
    implementation("io.ktor:ktor-client-cio-jvm:2.3.11")
}
