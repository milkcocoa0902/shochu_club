plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.googleKsp)
    alias(libs.plugins.kotlinx.rpc.platform)
    application
}

group = "com.milkcocoa.info.shochu_club.server.infra"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    // exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc) // or
    implementation(libs.exposed.kotlin.datetime)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
