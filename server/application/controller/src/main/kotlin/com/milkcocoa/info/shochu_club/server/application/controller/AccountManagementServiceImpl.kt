package com.milkcocoa.info.shochu_club.server.application.controller

import com.milkcocoa.info.shochu_club.models.AccountIdentifier
import com.milkcocoa.info.shochu_club.models.EmailAccountIdentifier
import com.milkcocoa.info.shochu_club.server.domain.model.IdToken
import com.milkcocoa.info.shochu_club.server.domain.service.AccountSignInService
import com.milkcocoa.info.shochu_club.server.service.AccountSignInWithEmailService
import com.milkcocoa.info.shochu_club.services.AccountManagementService
import io.ktor.server.routing.*
import kotlinx.rpc.serialization.protobuf
import kotlinx.rpc.transport.ktor.server.rpc
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import org.koin.ktor.ext.inject
import kotlin.coroutines.CoroutineContext

class AccountManagementServiceImpl(
    override val coroutineContext: CoroutineContext,
    val emailAccountService: AccountSignInService,
) : AccountManagementService {
    override suspend fun checkAccountExistence(identifier: AccountIdentifier): Boolean = true

    override suspend fun signInWithEmail(
        email: String,
        password: String,
    ): Result<EmailAccountIdentifier> {
        emailAccountService.signIn(
            credential = IdToken(value = ""),
        )
        TODO("not implemented yet")
    }

    override suspend fun signin(identifier: AccountIdentifier) {
        println("sign in")
    }

    override suspend fun signup(identifier: AccountIdentifier) {
        println("signup")
    }

    override suspend fun logout(identifier: AccountIdentifier) {
        println("logout")
    }

    override suspend fun deleteAccount(
        identifier: AccountIdentifier,
        deleteAccountToken: String,
    ) {
        println("deleteAccount")
    }
}

fun Route.accountRoute() {
    val emailAccountService: AccountSignInService by inject(
        qualifier = named(AccountSignInWithEmailService::class.java.simpleName),
    )
    rpc("/account") {
        rpcConfig {
            serialization {
                protobuf()
            }
        }

        registerService<AccountManagementService> { ctx -> AccountManagementServiceImpl(ctx, emailAccountService) }
    }
}
