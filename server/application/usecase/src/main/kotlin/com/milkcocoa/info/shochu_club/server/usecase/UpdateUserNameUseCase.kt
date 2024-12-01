package com.milkcocoa.info.shochu_club.server.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.AccountSummary
import com.milkcocoa.info.shochu_club.server.domain.model.User
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UpdateUserNameUseCase {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun execute(systemUid: Uuid, username: String): AccountSummary
}