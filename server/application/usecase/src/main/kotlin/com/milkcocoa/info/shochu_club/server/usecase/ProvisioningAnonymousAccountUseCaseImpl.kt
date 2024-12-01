package com.milkcocoa.info.shochu_club.server.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.ProvisionedUser
import com.milkcocoa.info.shochu_club.server.domain.service.AccountService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProvisioningAnonymousAccountUseCaseImpl(
    private val accountService: AccountService,
): ProvisioningAnonymousAccountUseCase {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun execute(
        systemUid: Uuid,
        email: String,
        passwordRaw: String,
        authProvider: Int
    ): ProvisionedUser{
        val validFormat =
            passwordRaw.length > 12 &&
            passwordRaw.any { it.isUpperCase() } &&
            passwordRaw.any { it.isLowerCase() } &&
            passwordRaw.any { it.isDigit() } &&
            passwordRaw.any { "!#_+/()".contains(it) }
        if (validFormat.not()){
            error("Invalid password format")
        }

        return accountService.provisionalSignUpFromAnonymousUser(
            systemUid = systemUid,
            email = email,
            passwordRaw = passwordRaw,
            authProvider = authProvider
        )
    }
}