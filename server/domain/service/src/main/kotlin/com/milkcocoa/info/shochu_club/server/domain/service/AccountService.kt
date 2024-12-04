package com.milkcocoa.info.shochu_club.server.domain.service

import com.milkcocoa.info.shochu_club.server.domain.model.*
import com.milkcocoa.info.shochu_club.server.domain.model.type.AuthProviderType
import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface AccountService {
    suspend fun signUpAnonymously(): Account.AnonymousUser

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateAnonymousUserInformation(
        systemUid: Uuid,
        nickname: String,
        comment: String
    ): Account.AnonymousUser

    /**
     *
     */
    @OptIn(ExperimentalUuidApi::class)
    suspend fun provisionalSignUpFromAnonymousUser(
        systemUid: Uuid,
        email: String,
        passwordRaw: String,
        authProvider: AuthProviderType
    ): Account.ProvisionedUser

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateUsername(systemUid: Uuid, username: String): Account

    suspend fun promoteProvisionedUser(
        email: String,
        passwordRaw: String,
        confirmationCode: String,
    ): Account.AuthenticatedUser
}
