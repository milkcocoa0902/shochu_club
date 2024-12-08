import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)

    alias(libs.plugins.jetbrains.kotlin.serialization)

    id("com.google.dagger.hilt.android")
    alias(libs.plugins.googleKsp)
    id("com.mikepenz.aboutlibraries.plugin")
    alias(libs.plugins.kotlinx.rpc)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.milkcocoa.info.shochu_club"
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()

//    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
//    sourceSets["main"].res.srcDirs("src/androidMain/res")
//    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)

    implementation(compose.preview)
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.ui)
    debugImplementation(compose.uiTooling)
    implementation(compose.components.resources)
    implementation(compose.components.uiToolingPreview)

    implementation(projects.shared)
    implementation(projects.composeApp.domain.model)

    implementation(libs.ktor.client.cio)
    implementation(libs.kotlinx.rpc.krpc.client)
    implementation(libs.kotlinx.rpc.krpc.ktor.client)
    implementation(libs.kotlinx.rpc.krpc.serialization.protobuf)

    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.aboutlibraries.core)
    implementation(libs.aboutlibraries)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.ui.auth)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)


    implementation(libs.kotlinx.datetime)
}
