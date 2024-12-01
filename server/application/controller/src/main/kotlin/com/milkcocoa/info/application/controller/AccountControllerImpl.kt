package com.milkcocoa.info.application.controller

import com.milkcocoa.info.shochu_club.server.domain.model.AccountSummary
import com.milkcocoa.info.shochu_club.server.domain.model.IdToken
import com.milkcocoa.info.shochu_club.server.domain.model.ProvisionedUser
import com.milkcocoa.info.shochu_club.server.domain.model.User
import com.milkcocoa.info.shochu_club.server.usecase.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AccountControllerImpl(
    private val createUserAnonymouslyUseCase: CreateUserAnonymouslyUseCase,
    private val updateAnonymousUserInfoUseCase: UpdateAnonymousUserInfoUseCase,
    private val updateUserNameUseCase: UpdateUserNameUseCase,
    private val provisioningAnonymousAccountUseCase: ProvisioningAnonymousAccountUseCase,
    private val promoteProvisionedAccountUseCase: PromoteProvisionedAccountUseCase
) : AccountController {
    override suspend fun signInWithEmail(credential: IdToken): User {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithGoogle(credential: IdToken): User {
        TODO("Not yet implemented")
    }

    override suspend fun signInAnonymously(): AccountSummary {
        return createUserAnonymouslyUseCase.execute()
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateAnonymousUserInfo(systemUid: Uuid, nickname: String, comment: String): AccountSummary {
        return updateAnonymousUserInfoUseCase.execute(systemUid, nickname, comment)
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateUserName(systemUid: Uuid, username: String): AccountSummary {
        return updateUserNameUseCase.execute(systemUid, username)
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun provisioningAnonymousAccount(
        systemUid: Uuid,
        email: String,
        passwordRaw: String,
        authProvider: Int
    ): ProvisionedUser {
        return provisioningAnonymousAccountUseCase.execute(systemUid, email, passwordRaw, authProvider)
    }

    override suspend fun promoteProvisionedAccount(
        email: String,
        passwordRaw: String,
        confirmationCode: String
    ): AccountSummary {
        return promoteProvisionedAccountUseCase.execute(email, passwordRaw, confirmationCode)
    }
}
