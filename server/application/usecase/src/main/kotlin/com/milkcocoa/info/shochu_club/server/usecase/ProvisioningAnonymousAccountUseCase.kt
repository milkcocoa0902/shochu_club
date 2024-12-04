package com.milkcocoa.info.shochu_club.server.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.model.type.AuthProviderType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface ProvisioningAnonymousAccountUseCase {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun execute(
        systemUid: Uuid,
        email: String,
        passwordRaw: String,
        authProvider: AuthProviderType
    ): Account.ProvisionedUser
}