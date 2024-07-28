package com.milkcocoa.info.shochu_club

import com.milkcocoa.info.shochu_club.models.AccountIdentifier
import com.milkcocoa.info.shochu_club.services.AccountManagementService
import com.milkcocoa.info.shochu_club.services.AwesomeService
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.rpc.serialization.protobuf
import kotlinx.rpc.transport.ktor.server.RPC
import kotlinx.rpc.transport.ktor.server.rpc
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import kotlin.coroutines.CoroutineContext

class AwesomeServiceImpl(
    override val coroutineContext: CoroutineContext,
) : AwesomeService {
    override suspend fun getNews(city: String): Flow<String> =
        flow {
            emit("Today is 23 degrees!")
            emit("Harry Potter is in $city!")
            emit("New dogs cafe has opened doors to all fluffy customers!")
        }
}

class AccountManagementServiceImpl(
    override val coroutineContext: CoroutineContext,
) : AccountManagementService {
    override suspend fun checkAccountExistence(identifier: AccountIdentifier): Boolean = true

    override suspend fun signin(identifier: AccountIdentifier) {
        println("sign in")
    }

    override suspend fun signup(identifier: AccountIdentifier) {
        println("signup")
    }

    override suspend fun logout(identifier: AccountIdentifier) {
        println("logout")
    }

    override suspend fun deleteAccount(
        identifier: AccountIdentifier,
        deleteAccountToken: String,
    ) {
        println("deleteAccount")
    }
}

fun Application.apiModule() {
    kotlin
        .runCatching {
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
            install(RPC)
            routing {
                rpc("/awesome") {
                    rpcConfig {
                        serialization {
                            protobuf()
                        }
                    }

                    registerService<AwesomeService> { ctx -> AwesomeServiceImpl(ctx) }
                    registerService<AccountManagementService> { ctx -> AccountManagementServiceImpl(ctx) }
                }
            }
        }.getOrElse {
            println(it)
        }
}
