package com.milkcocoa.info.shochu_club.server.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.service.AccountService

class PromoteProvisionedAccountUseCaseImpl(
    private val accountService: AccountService,
): PromoteProvisionedAccountUseCase {
    override suspend fun execute(
        email: String,
        passwordRaw: String,
        confirmationCode: String,
    ): Account.AuthenticatedUser {
        return accountService.promoteProvisionedUser(
            email = email,
            passwordRaw = passwordRaw,
            confirmationCode = confirmationCode,
        )
    }
}