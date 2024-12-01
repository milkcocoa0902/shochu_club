package com.milkcocoa.info.shochu_club.server.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.AccountSummary
import com.milkcocoa.info.shochu_club.server.domain.service.AccountService

class CreateUserAnonymouslyUseCaseImpl(
    private val accountService: AccountService
): CreateUserAnonymouslyUseCase {
    override suspend fun execute(): AccountSummary {
        return accountService.signUpAnonymously()
    }
}