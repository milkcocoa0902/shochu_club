package com.milkcocoa.info.shochu_club

import accountRoute
import appControllerModule
import appServiceModule
import com.google.firebase.FirebaseApp
import com.milkcocoa.info.shochu_club.server.infra.database.DataBaseConnectionInfo
import com.milkcocoa.info.shochu_club.server.infra.database.DataSourceType
import com.milkcocoa.info.shochu_club.server.infra.database.MigrationService
import dataSourceModule
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.rpc.krpc.ktor.server.RPC
import kotlinx.rpc.krpc.server.KRPCServer
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import repositoryModule
import useCaseModule

fun Application.apiModule() {
    FirebaseApp.initializeApp()

    install(Koin) {
        modules(
            dataSourceModule(),
            repositoryModule(),
            useCaseModule(),
            appServiceModule(),
            appControllerModule(),
        )
    }
    val databaseConnectionInfo: DataBaseConnectionInfo by inject()
    val migrationService: MigrationService by inject()

    databaseConnectionInfo.connect()
    migrationService.migrate()

    install(RPC)
    install(RoutingRoot) {
        accountRoute()
    }
}
