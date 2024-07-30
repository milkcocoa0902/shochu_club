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
    implementation(projects.server.domain.usecase)
    implementation(projects.server.domain.service)
    implementation(projects.server.usecase)
    implementation(projects.server.service)
    implementation(projects.server.infra.firebase)
    implementation(projects.server.infra.database)
    implementation(libs.koin.ktor)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
