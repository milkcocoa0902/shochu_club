package com.milkcocoa.info.shochu_club.server.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.AccountSummary
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UpdateAnonymousUserInfoUseCase {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun execute(
        systemUid: Uuid,
        nickname: String,
        comment: String,
    ): AccountSummary
}