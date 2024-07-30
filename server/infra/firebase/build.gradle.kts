plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.googleKsp)
    alias(libs.plugins.kotlinx.rpc.platform)
}

group = "com.milkcocoa.info.shochu_club.server.infra"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.server.domain.model)
    implementation(projects.server.domain.repository)
    api("com.google.firebase:firebase-admin:9.2.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
