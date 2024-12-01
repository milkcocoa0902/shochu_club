plugins {
    alias(libs.plugins.kotlinJvm)
}

group = "com.milkcocoa.info.shochu_club.server.infra"
version = "unspecified"

dependencies {
    implementation(projects.shared)

    implementation(projects.server.domain.model)
    implementation(projects.server.domain.repository)
    implementation(projects.server.domain.service)

    implementation(projects.server.application.usecase)
    implementation(projects.server.application.service)
    implementation(projects.server.application.controller)

    implementation(projects.server.infra.firebase)
    implementation(projects.server.infra.database)
    implementation(projects.server.infra.mail)
    implementation(projects.server.infra.cache)

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.ktor)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
