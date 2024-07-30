package com.milkcocoa.info.shochu_club

import appModule
import com.google.firebase.FirebaseApp
import com.milkcocoa.info.shochu_club.server.application.controller.accountRoute
import com.milkcocoa.info.shochu_club.server.application.controller.awesomeRoute
import com.milkcocoa.info.shochu_club.server.infra.database.DataSourceFactory
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.rpc.transport.ktor.server.RPC
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

fun Application.apiModule() {
    FirebaseApp.initializeApp()

    install(Koin) {
        modules(
            appModule,
        )
    }

    val dataSourceFactory: DataSourceFactory by inject()
    val dataSource =
        dataSourceFactory.dataSource(
            dbName = environment.config.propertyOrNull("ktor.database.db")?.getString() ?: "",
            dbHost = environment.config.propertyOrNull("ktor.database.host")?.getString() ?: "",
            dbPort =
                environment.config
                    .propertyOrNull("ktor.database.port")
                    ?.getString()
                    ?.toIntOrNull() ?: 3306,
            dbUser = environment.config.propertyOrNull("ktor.database.user")?.getString() ?: "",
            dbPassword = environment.config.propertyOrNull("ktor.database.password")?.getString() ?: "",
            poolSize = 50,
        )
    dataSourceFactory.connect(dataSource)
    dataSourceFactory.applyMigration(dataSource)

    install(RPC)
    routing {
        awesomeRoute()
        accountRoute()
    }
}
