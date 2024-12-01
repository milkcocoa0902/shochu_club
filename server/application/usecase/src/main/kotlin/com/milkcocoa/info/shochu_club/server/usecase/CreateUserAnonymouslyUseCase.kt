package com.milkcocoa.info.shochu_club.server.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.AccountSummary

interface CreateUserAnonymouslyUseCase {
    suspend fun execute(): AccountSummary
}