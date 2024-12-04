package com.milkcocoa.info.shochu_club

import DataSourceFactory
import MigrationFactory
import accountRoute
import appControllerModule
import appServiceModule
import com.google.firebase.FirebaseApp
import com.milkcocoa.info.shochu_club.server.infra.database.DataSourceType
import dataSourceModule
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.rpc.krpc.ktor.server.RPC
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
    val dataSourceFactory: DataSourceFactory by inject()
    val migrationFactory by inject<MigrationFactory>()

    dataSourceFactory.create(DataSourceType.MainDataSource).run {
        connect()
        migrationFactory.create(DataSourceType.MainDataSource).migrate()
    }
    install(RPC)
    install(RoutingRoot) {
        accountRoute()
    }
}
