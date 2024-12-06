plugins {
    kotlin("jvm")
}

group = "com.milkcocoa.info.shochu_club.server.infla"
version = "unspecified"


dependencies {
    implementation(platform(libs.aws.bom))
    implementation(libs.aws.s3)
    implementation(libs.aws.cloudfront)

    implementation(projects.server.domain.repository)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}