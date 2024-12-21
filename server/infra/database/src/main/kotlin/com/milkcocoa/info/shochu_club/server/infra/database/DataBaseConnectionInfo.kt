package com.milkcocoa.info.shochu_club.server.infra.database

import javax.sql.DataSource

enum class DatabaseDriver(
    val driverClassName: String,
    val connectionUrlSchema: String,
) {
    JdbcMariadb(
        driverClassName = "org.mariadb.jdbc.Driver",
        connectionUrlSchema = "jdbc:mariadb",
    ),
    JdbcPostgres(
        driverClassName = "org.postgresql.Driver",
        connectionUrlSchema = "jdbc:postgresql",
    )
}

interface DataBaseConnectionInfo {
    val databaseDriver: DatabaseDriver
    val dbName: String
    val dbHost: String
    val dbPort: Int
    val dbUser: String
    var dbPassword: String

    fun dataSource(): DataSource

    fun connect(): Unit

    fun updatePassword(password: String)
}
