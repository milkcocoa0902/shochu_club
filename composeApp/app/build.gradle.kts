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
    id("newrelic")
}

android {
    val localProperties = Properties()
    localProperties.load(project.rootProject.file("local.properties").inputStream())

    namespace = "com.milkcocoa.info.shochu_club"
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()

//    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
//    sourceSets["main"].res.srcDirs("src/androidMain/res")
//    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    signingConfigs {
        create("dev") {
            keyAlias = localProperties["devKey.alias"] as String
            storeFile = File(localProperties["devKey.file"] as String)
            storePassword = localProperties["devKey.storePassword"] as String
            keyPassword = localProperties["devKey.keyPassword"] as String
        }
        create("prod") {
            keyAlias = localProperties["prodKey.alias"] as String
            storeFile = File(localProperties["prodKey.file"] as String)
            storePassword = localProperties["prodKey.storePassword"] as String
            keyPassword = localProperties["prodKey.keyPassword"] as String
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
            buildConfigField(
                type = "String",
                name = "NewRelicApiKey",
                value = localProperties["newrelic.mobile.dev"] as String
            )
        }

        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("prod")
            buildConfigField(
                type = "String",
                name = "NewRelicApiKey",
                value = localProperties["newrelic.mobile.dev"] as String
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
    implementation("com.newrelic.agent.android:android-agent:7.6.2")
}
