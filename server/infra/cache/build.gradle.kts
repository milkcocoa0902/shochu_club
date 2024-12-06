plugins {
    kotlin("jvm")
}

group = "com.milkcocoa.info.shochu_club.server.infla"
version = "unspecified"

dependencies {
    implementation(libs.jedis)
    implementation(projects.server.domain.repository)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}