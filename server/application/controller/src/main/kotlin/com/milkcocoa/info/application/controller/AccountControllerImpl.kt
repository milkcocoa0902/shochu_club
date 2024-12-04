package com.milkcocoa.info.application.controller

import com.milkcocoa.info.shochu_club.server.domain.model.*
import com.milkcocoa.info.shochu_club.server.domain.model.type.AuthProviderType
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

    override suspend fun signInAnonymously(): Account.AnonymousUser {
        return createUserAnonymouslyUseCase.execute()
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateAnonymousUserInfo(systemUid: Uuid, nickname: String, comment: String): Account.AnonymousUser {
        return updateAnonymousUserInfoUseCase.execute(systemUid, nickname, comment)
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateUserName(systemUid: Uuid, username: String): Account {
        return updateUserNameUseCase.execute(systemUid, username)
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun provisioningAnonymousAccount(
        systemUid: Uuid,
        email: String,
        passwordRaw: String,
        authProvider: AuthProviderType
    ): Account.ProvisionedUser {
        return provisioningAnonymousAccountUseCase.execute(systemUid, email, passwordRaw, authProvider)
    }

    override suspend fun promoteProvisionedAccount(
        email: String,
        passwordRaw: String,
        confirmationCode: String
    ): Account.AuthenticatedUser {
        return promoteProvisionedAccountUseCase.execute(email, passwordRaw, confirmationCode)
    }
}
