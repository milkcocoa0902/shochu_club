plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

group = "com.milkcocoa.info.shochu_club.server.infla"
version = "unspecified"


dependencies {
    implementation(libs.aws.cloudfront)
    implementation(libs.aws.s3)
    implementation(libs.aws.secretsmanager)
    implementation(libs.kotlinx.serialization.json)

    implementation(projects.server.domain.repository)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}