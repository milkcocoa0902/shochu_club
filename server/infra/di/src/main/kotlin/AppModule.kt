import com.milkcocoa.info.shochu_club.server.domain.repository.AccountRepository
import com.milkcocoa.info.shochu_club.server.domain.repository.UserRepository
import com.milkcocoa.info.shochu_club.server.domain.service.AccountSignInService
import com.milkcocoa.info.shochu_club.server.domain.usecase.AccountSignInUseCase
import com.milkcocoa.info.shochu_club.server.domain.usecase.GetUserUseCase
import com.milkcocoa.info.shochu_club.server.infra.database.DataSourceFactory
import com.milkcocoa.info.shochu_club.server.infra.database.DataSourceFactoryImpl
import com.milkcocoa.info.shochu_club.server.infra.database.repository.UserRepositoryImpl
import com.milkcocoa.info.shochu_club.server.infra.firebase.EmailAccountRepositoryImpl
import com.milkcocoa.info.shochu_club.server.infra.firebase.GoogleAccountRepositoryImpl
import com.milkcocoa.info.shochu_club.server.service.AccountSignInWithEmailService
import com.milkcocoa.info.shochu_club.server.usecase.AccountSignInUseCaseImpl
import com.milkcocoa.info.shochu_club.server.usecase.GetUserUseCaseImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule =
    module {
        single<DataSourceFactory> { DataSourceFactoryImpl }

        single<UserRepository> { UserRepositoryImpl() }

        single<AccountRepository>(named(EmailAccountRepositoryImpl::class.java.simpleName)) { EmailAccountRepositoryImpl() }
        single<AccountRepository>(named(GoogleAccountRepositoryImpl::class.java.simpleName)) { GoogleAccountRepositoryImpl() }

        single<AccountSignInUseCase> { AccountSignInUseCaseImpl() }
        single<GetUserUseCase> { GetUserUseCaseImpl(get()) }

        single<AccountSignInService>(named(AccountSignInWithEmailService::class.java.simpleName)) {
            AccountSignInWithEmailService(
                accountRepository = get(named(EmailAccountRepositoryImpl::class.java.simpleName)),
                getUserUseCase = get(),
                accountSignInUseCase = get(),
            )
        }
    }
