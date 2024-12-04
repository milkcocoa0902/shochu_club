package com.milkcocoa.info.shochu_club.server.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.service.AccountService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

class UpdateAnonymousUserInfoUseCaseImpl(
    private val accountService: AccountService,
): UpdateAnonymousUserInfoUseCase {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun execute(systemUid: Uuid, nickname: String, comment: String): Account.AnonymousUser {
        return accountService.updateAnonymousUserInformation(
            systemUid = systemUid,
            nickname = nickname,
            comment = comment
        )
    }
}