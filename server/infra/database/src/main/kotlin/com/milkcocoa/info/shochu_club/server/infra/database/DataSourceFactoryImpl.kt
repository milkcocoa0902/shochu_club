package com.milkcocoa.info.shochu_club.server.infra.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

object DataSourceFactoryImpl : DataSourceFactory {
    private var connected: Boolean = false

    @Synchronized
    override fun connect(dataSource: DataSource) {
        if (connected) {
            return
        }
        connected = true
        Database.connect(dataSource)
    }

    override fun dataSource(
        dbName: String,
        dbHost: String,
        dbPort: Int,
        dbUser: String,
        dbPassword: String,
        poolSize: Int,
    ): HikariDataSource =
        HikariDataSource(
            HikariConfig().apply {
                jdbcUrl = "jdbc:mariadb://$dbHost:$dbPort/$dbName"
                driverClassName = "org.mariadb.jdbc.Driver"
                username = dbUser
                password = dbPassword
                maximumPoolSize = poolSize
            },
        )

    override fun applyMigration(dataSource: DataSource) {
        Flyway
            .configure()
            .dataSource(dataSource)
            .load()
            .migrate()
    }
}
