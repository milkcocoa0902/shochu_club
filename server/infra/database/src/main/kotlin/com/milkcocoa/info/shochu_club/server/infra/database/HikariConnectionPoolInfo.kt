package com.milkcocoa.info.shochu_club.server.infra.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import java.time.Duration
import javax.sql.DataSource

class HikariConnectionPoolInfo(
    override val databaseDriver: DatabaseDriver,
    override val dbName: String,
    override val dbHost: String,
    override val dbPort: Int,
    override val dbUser: String,
    override var dbPassword: String,
    val minPoolSize: Int,
    val maxPoolSize: Int,
    val idleTimeout: Duration?,
    val maxLifetime: Duration?,
) : DataBaseConnectionInfo {
    private val _dataSource by lazy {
        HikariDataSource(
            HikariConfig().apply hikariConfig@{
                jdbcUrl = "${databaseDriver.connectionUrlSchema}://$dbHost:$dbPort/$dbName"
                driverClassName = databaseDriver.driverClassName
                username = dbUser
                password = dbPassword
                maximumPoolSize = maxPoolSize
                minimumIdle = minPoolSize
                this@HikariConnectionPoolInfo.idleTimeout?.run {
                    this@hikariConfig.idleTimeout = this.toMillis()
                }
                this@HikariConnectionPoolInfo.maxLifetime?.run {
                    this@hikariConfig.maxLifetime = this.toMillis()
                }
            },
        )
    }

    private var isConnected: Boolean = false

    @Synchronized
    override fun connect() {
        kotlin.runCatching {
            if (isConnected) return@runCatching

            isConnected = true
            Database.connect(dataSource())
        }.getOrElse {
            isConnected = false
        }
    }

    override fun dataSource(): DataSource = _dataSource

    override fun updatePassword(password: String) {
        this.dbPassword = password
        this._dataSource.hikariConfigMXBean.setPassword(password)
    }
}
