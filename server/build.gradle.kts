plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.googleKsp)
    alias(libs.plugins.kotlinx.rpc.platform)
    application
}

group = "com.milkcocoa.info.shochu_club"
version = "1.0.0"
application {
    mainClass.set("io.ktor.server.cio.EngineMain")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)

    implementation(libs.dotenv.kotlin)

    implementation(libs.logback)

    // ktor
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.resources)
    implementation(libs.ktor.server.compression)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.default.headers)
    implementation(libs.ktor.server.config.yaml)

    // kotlin rpc
    implementation(libs.kotlinx.rpc.krpc.server)
    implementation(libs.kotlinx.rpc.krpc.serialization.protobuf)
    implementation(libs.kotlinx.rpc.krpc.ktor.server)

    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)

    // exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc) // or
    implementation(libs.exposed.kotlin.datetime)

    implementation(libs.mariadb.connector.java)

    implementation(libs.hikaricp)

    implementation(libs.flyway.core)
    implementation(libs.flyway.mysql)

//    implementation("com.kborowy:firebase-auth-provider:1.1.4")
//    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
//    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
//    implementation("com.h2database:h2:$h2_version")
//    implementation("ch.qos.logback:logback-classic:$logback_version")
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
