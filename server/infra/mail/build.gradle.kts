plugins {
    kotlin("jvm")
}

group = "com.milkcocoa.info.shochu_club.infla"
version = "unspecified"

dependencies {
    implementation("jakarta.mail:jakarta.mail-api:2.1.2")
    implementation("org.eclipse.angus:jakarta.mail:2.0.2")
    implementation(projects.server.domain.repository)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}