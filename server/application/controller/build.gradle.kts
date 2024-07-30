plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.googleKsp)
    alias(libs.plugins.kotlinx.rpc.platform)
}

group = "com.milkcocoa.info.shochu_club.server.application"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.server.domain.model)
    implementation(projects.server.domain.repository)
    implementation(projects.server.domain.usecase)
    implementation(projects.server.domain.service)
    implementation(projects.server.infra.database)
    implementation(projects.server.infra.firebase)
    implementation(projects.server.infra.di)

    implementation(projects.server.application.presenter)
    implementation(projects.server.service)

    implementation(libs.kotlinx.rpc.krpc.server)
    implementation(libs.kotlinx.rpc.krpc.serialization.protobuf)
    implementation(libs.kotlinx.rpc.krpc.ktor.server)
    implementation(projects.shared)
    implementation(libs.ktor.server.core)
    implementation(libs.koin.ktor)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
