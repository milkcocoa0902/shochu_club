package com.milkcocoa.info.shochu_club.server.infra.database

import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

interface DataSourceFactory {
    fun connect(dataSource: DataSource)

    fun dataSource(
        dbName: String,
        dbHost: String,
        dbPort: Int,
        dbUser: String,
        dbPassword: String,
        poolSize: Int,
    ): HikariDataSource

    fun applyMigration(dataSource: DataSource)
}
