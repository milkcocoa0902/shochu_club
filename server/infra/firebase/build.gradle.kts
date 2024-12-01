plugins {
    alias(libs.plugins.kotlinJvm)
}

group = "com.milkcocoa.info.shochu_club.server.infra"
version = "unspecified"

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
