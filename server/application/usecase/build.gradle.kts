plugins {
    alias(libs.plugins.kotlinJvm)
}

group = "com.milkcocoa.info.shochu_club.server"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.server.domain.model)
    implementation(projects.server.domain.repository)
    implementation(projects.server.domain.service)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
