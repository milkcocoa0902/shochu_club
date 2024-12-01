plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.kotlinx.rpc)
}

group = "com.milkcocoa.info.shochu_club.server.application"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.server.presentation.controller)

    implementation(projects.server.domain.model)
    implementation(projects.server.domain.service)

    implementation(projects.shared)

    implementation(libs.kotlinx.datetime)
    testImplementation(kotlin("test"))
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.resources)

    implementation(libs.kotlinx.rpc.krpc.server)
    implementation(libs.kotlinx.rpc.krpc.ktor.server)
    implementation(libs.kotlinx.rpc.krpc.serialization.protobuf)

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.ktor)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
