package com.milkcocoa.info.evessa_fan_app.server.infra.database

import org.flywaydb.core.Flyway

class FlywayMigrationService(
    val connectionInfo: DataBaseConnectionInfo,
    val flywayLocations: Array<String>,
) : MigrationService {
    override fun migrate() {
        Flyway
            .configure()
            .dataSource(connectionInfo.dataSource())
            .locations(*flywayLocations)
            .load()
            .migrate()
    }
}
