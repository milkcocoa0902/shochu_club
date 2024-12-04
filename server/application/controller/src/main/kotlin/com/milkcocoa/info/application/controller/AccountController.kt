package com.milkcocoa.info.application.controller

import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.model.IdToken
import com.milkcocoa.info.shochu_club.server.domain.model.User
import com.milkcocoa.info.shochu_club.server.domain.model.type.AuthProviderType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface AccountController {
    suspend fun signInWithEmail(credential: IdToken): User

    suspend fun signInWithGoogle(credential: IdToken): User

    suspend fun signInAnonymously(): Account.AnonymousUser

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateAnonymousUserInfo(
        systemUid: Uuid,
        nickname: String,
        comment: String
    ): Account.AnonymousUser

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateUserName(
        systemUid: Uuid,
        username: String,
    ): Account

    @OptIn(ExperimentalUuidApi::class)
    suspend fun provisioningAnonymousAccount(
        systemUid: Uuid,
        email: String,
        passwordRaw: String,
        authProvider: AuthProviderType
    ): Account.ProvisionedUser

    @OptIn(ExperimentalUuidApi::class)
    suspend fun promoteProvisionedAccount(
        email: String,
        passwordRaw: String,
        confirmationCode: String
    ): Account.AuthenticatedUser
}
