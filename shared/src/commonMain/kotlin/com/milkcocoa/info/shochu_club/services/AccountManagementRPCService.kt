package com.milkcocoa.info.shochu_club.services

import com.milkcocoa.info.shochu_club.client.ktorRpcClient
import com.milkcocoa.info.shochu_club.models.AccountIdentifier
import com.milkcocoa.info.shochu_club.models.IdTokenAccountIdentifier
import com.milkcocoa.info.shochu_club.models.SystemUid
import com.milkcocoa.info.shochu_club.models.details.Result
import com.milkcocoa.info.shochu_club.models.user.ShochuClubUserDetail
import com.milkcocoa.info.shochu_club.models.user.ShochuClubUserSummary
import kotlinx.rpc.RemoteService
import kotlinx.rpc.annotations.Rpc
import kotlinx.rpc.withService
import kotlin.uuid.ExperimentalUuidApi

@Rpc
interface AccountManagementRPCService : RemoteService {
    suspend fun checkAccountExistence(
        identifier: AccountIdentifier
    ): Boolean

    suspend fun signInWithEmail(
        idToken: IdTokenAccountIdentifier
    ): Result<ShochuClubUserDetail>

    suspend fun signInWithGoogle(
        idToken: IdTokenAccountIdentifier
    ): Result<ShochuClubUserDetail>

    /**
     * システムでベアラートークンを発行する
     */
    suspend fun signInAnonymously(): Result<ShochuClubUserSummary.AnonymousUser>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateAnonymousUserInfo(
        systemUid: SystemUid,
        nickname: String,
        comment: String
    ): Result<ShochuClubUserSummary.AnonymousUser>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateUserName(
        systemUid: SystemUid,
        username: String
    ): Result<ShochuClubUserSummary>


    suspend fun signin(
        identifier: AccountIdentifier
    )

    suspend fun signupWithEmail(
        idToken: IdTokenAccountIdentifier
    ): Result<ShochuClubUserDetail>

    suspend fun logout(
        identifier: AccountIdentifier
    )

    suspend fun deleteAccount(
        identifier: AccountIdentifier,
        deleteAccountToken: String,
    )

    suspend fun provisioningAnonymousAccount(
        systemUid: SystemUid,
        email: String,
        passwordRaw: String,
        authProvider: Int
    ): Result<ShochuClubUserSummary.ProvisionedUser>

    suspend fun promoteProvisionedAccount(
        email: String,
        passwordRaw: String,
        confirmationCode: String
    ): Result<ShochuClubUserSummary.AuthenticatedUser>
}

suspend fun accountManagementRpcClient(urlString: String) = ktorRpcClient(urlString).withService<AccountManagementRPCService>()