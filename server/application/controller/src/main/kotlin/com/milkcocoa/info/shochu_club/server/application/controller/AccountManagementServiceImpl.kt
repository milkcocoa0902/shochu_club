package com.milkcocoa.info.shochu_club.server.application.controller

import com.milkcocoa.info.shochu_club.models.AccountIdentifier
import com.milkcocoa.info.shochu_club.services.AccountManagementService
import io.ktor.server.routing.*
import kotlinx.rpc.serialization.protobuf
import kotlinx.rpc.transport.ktor.server.rpc
import kotlin.coroutines.CoroutineContext

class AccountManagementServiceImpl(
    override val coroutineContext: CoroutineContext,
) : AccountManagementService {
    override suspend fun checkAccountExistence(identifier: AccountIdentifier): Boolean = true

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
    rpc("/account") {
        rpcConfig {
            serialization {
                protobuf()
            }
        }

        registerService<AccountManagementService> { ctx -> AccountManagementServiceImpl(ctx) }
    }
}
