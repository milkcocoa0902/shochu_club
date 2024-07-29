import com.milkcocoa.info.shochu_club.server.infra.database.DataSourceFactory
import com.milkcocoa.info.shochu_club.server.infra.database.DataSourceFactoryImpl
import org.koin.dsl.module

val appModule =
    module {
        single<DataSourceFactory> { DataSourceFactoryImpl }
    }
