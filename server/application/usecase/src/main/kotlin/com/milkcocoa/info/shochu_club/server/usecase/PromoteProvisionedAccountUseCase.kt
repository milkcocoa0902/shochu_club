package com.milkcocoa.info.shochu_club.server.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.Account

interface PromoteProvisionedAccountUseCase {
    suspend fun execute(
        email: String,
        passwordRaw: String,
        confirmationCode: String,
    ): Account.AuthenticatedUser
}