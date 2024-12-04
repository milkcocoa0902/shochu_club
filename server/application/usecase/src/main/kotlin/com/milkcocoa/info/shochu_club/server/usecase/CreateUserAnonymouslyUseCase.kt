package com.milkcocoa.info.shochu_club.server.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.Account

interface CreateUserAnonymouslyUseCase {
    suspend fun execute(): Account.AnonymousUser
}