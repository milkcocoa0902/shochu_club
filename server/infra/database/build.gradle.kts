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

    // exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc) // or
    implementation(libs.mariadb.connector.java)
    implementation(libs.exposed.kotlin.datetime)
    api(libs.hikaricp)

    implementation(libs.flyway.core)
    implementation(libs.flyway.mysql)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
