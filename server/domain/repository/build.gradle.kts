plugins {
    alias(libs.plugins.kotlinJvm)
}

group = "com.milkcocoa.info.shochu_club.server.domain"
version = "unspecified"

dependencies {
    implementation(projects.server.domain.model)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
