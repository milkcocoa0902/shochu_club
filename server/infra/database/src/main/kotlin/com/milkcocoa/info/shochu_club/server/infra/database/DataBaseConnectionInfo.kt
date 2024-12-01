package com.milkcocoa.info.evessa_fan_app.server.infra.database

import javax.sql.DataSource

enum class DatabaseDriver(
    val driverClassName: String,
    val connectionUrlSchema: String,
) {
    JdbcMariadb(
        driverClassName = "org.mariadb.jdbc.Driver",
        connectionUrlSchema = "jdbc:mariadb",
    ),
}

interface DataBaseConnectionInfo {
    val databaseDriver: DatabaseDriver
    val dbName: String
    val dbHost: String
    val dbPort: Int
    val dbUser: String
    val dbPassword: String
    val poolSize: Int

    fun dataSource(): DataSource

    fun connect(): Unit
}
