import com.milkcocoa.info.application.controller.AccountController
import com.milkcocoa.info.application.controller.AccountControllerImpl
import com.milkcocoa.info.evessa_fan_app.server.infra.database.*
import com.milkcocoa.info.shochu_club.server.cache.RedisCacheRepository
import com.milkcocoa.info.shochu_club.server.domain.repository.AccountRepository
import com.milkcocoa.info.shochu_club.server.domain.repository.CacheRepository
import com.milkcocoa.info.shochu_club.server.domain.repository.MailBackend
import com.milkcocoa.info.shochu_club.server.domain.service.AccountService
import com.milkcocoa.info.shochu_club.server.infla.mail.ConsoleBackend
import com.milkcocoa.info.shochu_club.server.infra.database.DataSourceType
import com.milkcocoa.info.shochu_club.server.infra.database.repository.AccountRepositoryImpl
import com.milkcocoa.info.shochu_club.server.service.AccountServiceImpl
import com.milkcocoa.info.shochu_club.server.usecase.*
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject

enum class DataSource {
    MainDataSource,
}

interface DataSourceFactory {
    fun create(dataSourceType: DataSourceType): DataBaseConnectionInfo
}

interface MigrationFactory {
    fun create(dataSourceType: DataSourceType): MigrationService
}

class MigrationFactoryImpl(
    private val environment: ApplicationEnvironment,
) : MigrationFactory {
    override fun create(dataSourceType: DataSourceType): MigrationService =
        when (dataSourceType) {
            DataSourceType.MainDataSource -> {
                val flywayLocations =
                    environment.config
                        .propertyOrNull("ktor.database.flyway.locations")
                        ?.getList()
                        ?.toTypedArray()
                        ?: emptyList<String>().toTypedArray()

                val dataSourceFactory: DataSourceFactory by inject(DataSourceFactory::class.java)
                FlywayMigrationService(
                    connectionInfo = dataSourceFactory.create(dataSourceType),
                    flywayLocations = flywayLocations,
                )
            }
        }
}

class DataSourceFactoryImpl(
    private val environment: ApplicationEnvironment,
) : DataSourceFactory {
    override fun create(dataSourceType: DataSourceType): DataBaseConnectionInfo {
        when (dataSourceType) {
            DataSourceType.MainDataSource -> {
                val dbName = environment.config.propertyOrNull("ktor.database.db")?.getString() ?: ""
                val dbHost = environment.config.propertyOrNull("ktor.database.host")?.getString() ?: ""
                val dbPort =
                    environment.config
                        .propertyOrNull("ktor.database.port")
                        ?.getString()
                        ?.toInt()!!
                val dbUser = environment.config.propertyOrNull("ktor.database.user")?.getString() ?: ""
                val dbPassword = environment.config.propertyOrNull("ktor.database.password")?.getString() ?: ""
                val dbDriver = environment.config.propertyOrNull("ktor.database.driver")?.getString() ?: ""
                val poolSize = 50

                return DataBaseConnectionInfoImpl(
                    databaseDriver = DatabaseDriver.valueOf(dbDriver),
                    dbName = dbName,
                    dbHost = dbHost,
                    dbPort = dbPort,
                    dbUser = dbUser,
                    dbPassword = dbPassword,
                    poolSize = poolSize,
                )
            }
        }
    }
}

fun Application.repositoryModule() =
    module {
        single<AccountRepository>{
            AccountRepositoryImpl()
        }
        single<CacheRepository>{
            RedisCacheRepository(
                host = environment.config.propertyOrNull("ktor.cache.host")?.getString() ?: "localhost",
                port = environment.config.propertyOrNull("ktor.cache.port")?.getString()?.toIntOrNull() ?: 6379,
                password = environment.config.propertyOrNull("ktor.cache.password")?.getString(),
                useSsl = false
            )
        }
        single<MailBackend> {
            ConsoleBackend()
        }
    }

fun Application.appServiceModule() =
    module {
        single<AccountService> {
            AccountServiceImpl(
                accountRepository = get(),
                cacheRepository = get(),
                mailBackend = get(),
            )
        }
    }

fun Application.useCaseModule() =
    module {
        single<CreateUserAnonymouslyUseCase> {
            CreateUserAnonymouslyUseCaseImpl(accountService = get())
        }

        single<UpdateUserNameUseCase>{
            UpdateUserNameUseCaseImpl(accountService = get())
        }

        single<UpdateAnonymousUserInfoUseCase> {
            UpdateAnonymousUserInfoUseCaseImpl(accountService = get())
        }

        single<ProvisioningAnonymousAccountUseCase> {
            ProvisioningAnonymousAccountUseCaseImpl(accountService = get())
        }
        single<PromoteProvisionedAccountUseCase>{
            PromoteProvisionedAccountUseCaseImpl(accountService = get())
        }
    }

fun Application.appControllerModule() =
    module {
        single<AccountController> {
            AccountControllerImpl(
                createUserAnonymouslyUseCase = get(),
                updateAnonymousUserInfoUseCase = get(),
                updateUserNameUseCase = get(),
                provisioningAnonymousAccountUseCase = get(),
                promoteProvisionedAccountUseCase = get(),
            )
        }
    }

fun Application.dataSourceModule() =
    module {
        single<DataSourceFactory> {
            DataSourceFactoryImpl(environment)
        }

        single<MigrationFactory> {
            MigrationFactoryImpl(environment)
        }
    }
