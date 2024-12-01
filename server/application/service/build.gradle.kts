plugins {
    alias(libs.plugins.kotlinJvm)
}

group = "com.milkcocoa.info.shochu_club.server"
version = "unspecified"

dependencies {
    implementation(projects.server.domain.model)
    implementation(projects.server.domain.repository)
    implementation(projects.server.domain.service)
    implementation(libs.exposed.core)
    testImplementation(kotlin("test"))

    implementation(libs.argon2.jvm)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
