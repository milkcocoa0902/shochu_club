package com.milkcocoa.info.shochu_club

import com.milkcocoa.info.shochu_club.server.application.controller.accountRoute
import com.milkcocoa.info.shochu_club.server.application.controller.awesomeRoute
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.rpc.transport.ktor.server.RPC
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.koin.ktor.plugin.Koin

fun Application.apiModule() {
    val dbName = environment.config.propertyOrNull("ktor.database.db")?.getString() ?: ""
    val dbHost = environment.config.propertyOrNull("ktor.database.host")?.getString() ?: ""
    val dbPort = environment.config.propertyOrNull("ktor.database.port")?.getString() ?: ""
    val dbDriver = environment.config.propertyOrNull("ktor.database.driver")?.getString() ?: ""
    val dbUser = environment.config.propertyOrNull("ktor.database.user")?.getString() ?: ""
    val dbPassword = environment.config.propertyOrNull("ktor.database.password")?.getString() ?: ""

    HikariConfig()
        .apply hikari@{
            this.jdbcUrl = "jdbc:mariadb://$dbHost:$dbPort/$dbName"
            driverClassName = dbDriver
            username = dbUser
            password = dbPassword
            maximumPoolSize = 100
        }.let {
            HikariDataSource(it)
        }.apply {
            Database.connect(this)
            Flyway
                .configure()
                .dataSource(this)
                .load()
                .migrate()
        }
    install(Koin)
    install(RPC)
    routing {
        awesomeRoute()
        accountRoute()
    }
}
