package com.milkcocoa.info.shochu_club.server.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.AccountSummary
import com.milkcocoa.info.shochu_club.server.domain.model.User
import com.milkcocoa.info.shochu_club.server.domain.service.AccountService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

class UpdateUserNameUseCaseImpl(
    private val accountService: AccountService
): UpdateUserNameUseCase {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun execute(systemUid: Uuid, username: String): AccountSummary{
        return accountService.updateUsername(
            systemUid = systemUid,
            username = username
        )
    }
}