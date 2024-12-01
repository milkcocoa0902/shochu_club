package com.milkcocoa.info.evessa_fan_app.server.infra.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

class DataBaseConnectionInfoImpl(
    override val databaseDriver: DatabaseDriver,
    override val dbName: String,
    override val dbHost: String,
    override val dbPort: Int,
    override val dbUser: String,
    override val dbPassword: String,
    override val poolSize: Int,
) : DataBaseConnectionInfo {
    private val _dataSource by lazy {
        HikariDataSource(
            HikariConfig().apply {
                jdbcUrl = "${databaseDriver.connectionUrlSchema}://$dbHost:$dbPort/$dbName"
                driverClassName = databaseDriver.driverClassName
                username = dbUser
                password = dbPassword
                maximumPoolSize = poolSize
            },
        )
    }

    @Synchronized
    override fun connect() {
        Database.connect(dataSource())
    }

    override fun dataSource(): DataSource = _dataSource
}
