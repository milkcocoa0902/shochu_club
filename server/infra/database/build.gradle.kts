plugins {
    alias(libs.plugins.kotlinJvm)
}

group = "com.milkcocoa.info.shochu_club.server.infra"
version = "unspecified"

dependencies {
    implementation(projects.server.domain.model)
    implementation(projects.server.domain.repository)

    // exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc) // or
    implementation(libs.exposed.kotlin.datetime)
    api(libs.hikaricp)

    implementation(libs.flyway.core)
    implementation(libs.flyway.mysql)
    implementation(libs.flyway.postgresql)
    testImplementation(kotlin("test"))
    implementation(libs.kotlinx.datetime)

    implementation(libs.mariadb.connector.java)
    implementation(libs.postgresql)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
