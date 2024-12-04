package com.milkcocoa.info.shochu_club.server.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.service.AccountService

class CreateUserAnonymouslyUseCaseImpl(
    private val accountService: AccountService
): CreateUserAnonymouslyUseCase {
    override suspend fun execute(): Account.AnonymousUser {
        return accountService.signUpAnonymously()
    }
}