package com.milkcocoa.info.application.controller

import com.milkcocoa.info.shochu_club.server.domain.model.AccountSummary
import com.milkcocoa.info.shochu_club.server.domain.model.IdToken
import com.milkcocoa.info.shochu_club.server.domain.model.ProvisionedUser
import com.milkcocoa.info.shochu_club.server.domain.model.User
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface AccountController {
    suspend fun signInWithEmail(credential: IdToken): User

    suspend fun signInWithGoogle(credential: IdToken): User

    suspend fun signInAnonymously(): AccountSummary

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateAnonymousUserInfo(
        systemUid: Uuid,
        nickname: String,
        comment: String
    ): AccountSummary

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateUserName(
        systemUid: Uuid,
        username: String,
    ): AccountSummary

    @OptIn(ExperimentalUuidApi::class)
    suspend fun provisioningAnonymousAccount(
        systemUid: Uuid,
        email: String,
        passwordRaw: String,
        authProvider: Int
    ): ProvisionedUser

    @OptIn(ExperimentalUuidApi::class)
    suspend fun promoteProvisionedAccount(
        email: String,
        passwordRaw: String,
        confirmationCode: String
    ): AccountSummary
}
