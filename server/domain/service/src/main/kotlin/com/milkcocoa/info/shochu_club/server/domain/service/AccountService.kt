package com.milkcocoa.info.shochu_club.server.domain.service

import com.milkcocoa.info.shochu_club.server.domain.model.AccountSummary
import com.milkcocoa.info.shochu_club.server.domain.model.IdToken
import com.milkcocoa.info.shochu_club.server.domain.model.ProvisionedUser
import com.milkcocoa.info.shochu_club.server.domain.model.User
import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface AccountService {
    suspend fun signUpAnonymously(): AccountSummary
    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateAnonymousUserInformation(
        systemUid: Uuid,
        nickname: String,
        comment: String
    ): AccountSummary

    /**
     *
     */
    @OptIn(ExperimentalUuidApi::class)
    suspend fun provisionalSignUpFromAnonymousUser(
        systemUid: Uuid,
        email: String,
        passwordRaw: String,
        authProvider: Int
    ): ProvisionedUser

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateUsername(systemUid: Uuid, username: String): AccountSummary

    suspend fun promoteProvisionedUser(
        email: String,
        passwordRaw: String,
        confirmationCode: String,
    ): AccountSummary
}
