plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.kotlinx.rpc)
}

group = "com.milkcocoa.info.shochu_club.server.application"
version = "unspecified"

dependencies {
    implementation(projects.server.domain.model)
    implementation(projects.server.domain.repository)
    implementation(projects.server.domain.service)
    implementation(projects.server.infra.database)
    implementation(projects.server.infra.firebase)
    implementation(projects.server.infra.di)

    implementation(projects.server.application.controller)

    implementation(libs.kotlinx.rpc.krpc.server)
    implementation(libs.kotlinx.rpc.krpc.serialization.protobuf)
    implementation(libs.kotlinx.rpc.krpc.ktor.server)

    implementation(libs.kotlinx.serialization.core)


    implementation(projects.shared)

    implementation(libs.ktor.server.core)


    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.ktor)

    testImplementation(kotlin("test"))
    implementation(projects.shared)
    implementation(libs.kotlinx.datetime)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
