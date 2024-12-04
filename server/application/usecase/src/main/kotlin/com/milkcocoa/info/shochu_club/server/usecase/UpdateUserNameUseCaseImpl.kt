package com.milkcocoa.info.shochu_club.server.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.service.AccountService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class UpdateUserNameUseCaseImpl(
    private val accountService: AccountService
): UpdateUserNameUseCase {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun execute(systemUid: Uuid, username: String): Account {
        return accountService.updateUsername(
            systemUid = systemUid,
            username = username
        )
    }
}