plugins {
    kotlin("jvm")
}

group = "com.milkcocoa.info.shochu_club.server.infla"
version = "unspecified"

dependencies {
    implementation("redis.clients:jedis:5.2.0")
    implementation(projects.server.domain.repository)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}