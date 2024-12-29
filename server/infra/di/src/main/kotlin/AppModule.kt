import com.milkcocoa.info.application.controller.AccountController
import com.milkcocoa.info.application.controller.AccountControllerImpl
import com.milkcocoa.info.application.controller.TimelineController
import com.milkcocoa.info.application.controller.TimelineControllerImpl
import com.milkcocoa.info.evessa_fan_app.server.infra.database.*
import com.milkcocoa.info.shochu_club.server.cache.RedisCacheRepository
import com.milkcocoa.info.shochu_club.server.domain.repository.*
import com.milkcocoa.info.shochu_club.server.domain.service.AccountService
import com.milkcocoa.info.shochu_club.server.domain.service.TimelineService
import com.milkcocoa.info.shochu_club.server.infla.mail.ConsoleBackend
import com.milkcocoa.info.shochu_club.server.infra.aws.RdsSecretProvider
import com.milkcocoa.info.shochu_club.server.infra.aws.S3DistributionBackend
import com.milkcocoa.info.shochu_club.server.infra.database.*
import com.milkcocoa.info.shochu_club.server.infra.database.repository.AccountRepositoryImpl
import com.milkcocoa.info.shochu_club.server.infra.database.repository.FeedRepositoryImpl
import com.milkcocoa.info.shochu_club.server.service.AccountServiceImpl
import com.milkcocoa.info.shochu_club.server.service.TimelineServiceImpl
import com.milkcocoa.info.shochu_club.server.usecase.*
import io.ktor.http.*
import io.ktor.server.application.*
import kotlinx.coroutines.runBlocking
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.net.URL
import java.time.Duration


fun Application.repositoryModule() =
    module {
        single<FeedRepository>{
            FeedRepositoryImpl()
        }
        single<AccountRepository>{
            AccountRepositoryImpl()
        }
        single<FeedRepository>{
            FeedRepositoryImpl()
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
        single<DistributionBackend>{
            S3DistributionBackend(
                bucket = environment.config.propertyOrNull("ktor.storage.bucket")?.getString() ?: error(""),
                region = environment.config.propertyOrNull("ktor.storage.region")?.getString() ?: error(""),
                endpointUrl = environment.config.propertyOrNull("ktor.storage.endpoint")?.getString()?.let { URL(it) }
            )
        }
    }

fun Application.appServiceModule() =
    module {
        single<TimelineService>{
            TimelineServiceImpl(
                feedRepository = get(),
                distributionBackend = get(),
            )
        }
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
        single<HomeTimelineUseCase>{
            HomeTimelineUseCaseImpl(
                timelineService = get()
            )
        }
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
        single<TimelineController>{
            TimelineControllerImpl(
                homeTimelineUseCase = get()
            )
        }
    }

private enum class DatabasePasswordProviderSelection(val key: String){
    Raw(key = "Raw"),
    AwsSecretManager(key = "ASM");

    companion object{
        fun of(key: String?) = entries.find { it.key == key }
    }

}

fun Application.dataSourceModule() =
    module {

        single<DatabasePasswordProvider>(named(enum = DatabasePasswordProviderSelection.Raw)) {
            DatabaseRawPasswordProvider(
                environment.config.propertyOrNull("ktor.database.password.value")?.getString() ?: error("no database password was provided")
            )
        }

        single<DatabasePasswordProvider>(named(enum = DatabasePasswordProviderSelection.AwsSecretManager)){
            RdsSecretProvider(
                secretName = environment.config.propertyOrNull("ktor.database.password.provider.secret")?.getString() ?: error("no secret was provided"),
                region = environment.config.propertyOrNull("ktor.database.password.provider.region")?.getString() ?: error("no region was provided")
            )
        }
        single<DataBaseConnectionInfo> {
            val dbName = environment.config.propertyOrNull("ktor.database.db")?.getString() ?: ""
            val dbHost = environment.config.propertyOrNull("ktor.database.host")?.getString() ?: ""
            val dbPort =
                environment.config
                    .propertyOrNull("ktor.database.port")
                    ?.getString()
                    ?.toInt()!!
            val dbUser = environment.config.propertyOrNull("ktor.database.user")?.getString() ?: ""
            val dbPassword = when(DatabasePasswordProviderSelection.of(environment.config.propertyOrNull("ktor.database.password.provider.type")?.getString())) {
                DatabasePasswordProviderSelection.Raw -> {
                    val provider: DatabasePasswordProvider = get(named(enum = DatabasePasswordProviderSelection.Raw))
                    runBlocking {
                        provider.get()
                    }
                }
                DatabasePasswordProviderSelection.AwsSecretManager ->{
                    val provider: DatabasePasswordProvider = get(named(enum = DatabasePasswordProviderSelection.AwsSecretManager))
                    runBlocking {
                        provider.get()
                    }
                }
                else -> error("no password provider was found")
            }

            val dbDriver = environment.config.propertyOrNull("ktor.database.driver")?.getString() ?: ""
            val minPoolSize = environment.config.propertyOrNull("ktor.database.pool.min")?.getString()?.toIntOrNull() ?: 15
            val maxPoolSize = environment.config.propertyOrNull("ktor.database.pool.max")?.getString()?.toIntOrNull() ?: minPoolSize
            val idleTimeout = environment.config.propertyOrNull("ktor.database.pool.idle")?.getString()?.toLongOrNull()?.let { Duration.ofSeconds(it) }
            val maxLifeTime = environment.config.propertyOrNull("ktor.database.pool.lifetime")?.getString()?.toLongOrNull()?.let { Duration.ofSeconds(it) }


            HikariConnectionPoolInfo(
                databaseDriver = DatabaseDriver.valueOf(dbDriver),
                dbName = dbName,
                dbHost = dbHost,
                dbPort = dbPort,
                dbUser = dbUser,
                dbPassword = dbPassword!!,
                minPoolSize = minPoolSize,
                maxPoolSize = maxPoolSize,
                idleTimeout = idleTimeout,
                maxLifetime = maxLifeTime
            )
        }

        single<MigrationService> {
            val flywayLocations =
                environment.config
                    .propertyOrNull("ktor.database.flyway.locations")
                    ?.getList()
                    ?.toTypedArray()
                    ?: emptyList<String>().toTypedArray()

            FlywayMigrationService(
                connectionInfo = get(),
                flywayLocations = flywayLocations,
            )
        }
    }
