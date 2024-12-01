package com.milkcocoa.info.shochu_club.server.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.ProvisionedUser
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface ProvisioningAnonymousAccountUseCase {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun execute(
        systemUid: Uuid,
        email: String,
        passwordRaw: String,
        authProvider: Int
    ): ProvisionedUser
}