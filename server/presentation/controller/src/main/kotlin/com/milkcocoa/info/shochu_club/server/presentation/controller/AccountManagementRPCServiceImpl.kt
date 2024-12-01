package com.milkcocoa.info.shochu_club.server.presentation.controller

import com.milkcocoa.info.application.controller.AccountController
import com.milkcocoa.info.shochu_club.models.AccountIdentifier
import com.milkcocoa.info.shochu_club.models.IdTokenAccountIdentifier
import com.milkcocoa.info.shochu_club.models.SystemUid
import com.milkcocoa.info.shochu_club.models.details.ErrorMessage
import com.milkcocoa.info.shochu_club.models.details.Result
import com.milkcocoa.info.shochu_club.models.user.ShochuClubUserDetail
import com.milkcocoa.info.shochu_club.models.user.ShochuClubUserId
import com.milkcocoa.info.shochu_club.models.user.ShochuClubUserSummary
import com.milkcocoa.info.shochu_club.services.AccountManagementRPCService
import kotlinx.datetime.toKotlinLocalDateTime
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDateTime
import kotlin.coroutines.CoroutineContext
import kotlin.uuid.ExperimentalUuidApi

class AccountManagementRPCServiceImpl(
    override val coroutineContext: CoroutineContext,
) : AccountManagementRPCService {
    private val accountController: AccountController by inject(AccountController::class.java)

    override suspend fun checkAccountExistence(identifier: AccountIdentifier): Boolean = true

    override suspend fun signInWithEmail(idToken: IdTokenAccountIdentifier): Result<ShochuClubUserDetail>{
        TODO("Not yet implemented")
    }

    override suspend fun signInWithGoogle(idToken: IdTokenAccountIdentifier): Result<ShochuClubUserDetail> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun signInAnonymously(): Result<ShochuClubUserSummary.AnonymousUser> {
        return kotlin.runCatching {
            val user = accountController.signInAnonymously()
            return@runCatching Result.Success(
                ShochuClubUserSummary.AnonymousUser(
                    shochuClubUserId = ShochuClubUserId(value = user.systemUid),
                    nickname = user.nickName,
                    iconUrl = user.iconUrl,
                    registeredAt = LocalDateTime.now().toKotlinLocalDateTime()
                )
            )
        }.getOrElse {
            println(it)
            Result.Failure(
                listOf(
                    ErrorMessage.UnhandledError
                )
            )
        }
    }

    override suspend fun signin(identifier: AccountIdentifier) {
        println("sign in")
    }

    override suspend fun signupWithEmail(idToken: IdTokenAccountIdentifier): Result<ShochuClubUserDetail> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(identifier: AccountIdentifier) {
        println("logout")
    }

    override suspend fun deleteAccount(
        identifier: AccountIdentifier,
        deleteAccountToken: String,
    ) {
        println("deleteAccount")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun provisioningAnonymousAccount(
        systemUid: SystemUid,
        email: String,
        passwordRaw: String,
        authProvider: Int
    ): Result<ShochuClubUserSummary.ProvisionedUser> {
        return kotlin.runCatching {
            val user = accountController.provisioningAnonymousAccount(
                systemUid = systemUid.uid,
                email = email,
                passwordRaw = passwordRaw,
                authProvider = authProvider
            )
            return@runCatching Result.Success(
                ShochuClubUserSummary.ProvisionedUser(provisionedUserId = user.provisionedUserId)
            )
        }.getOrElse {
            println(it)
            Result.Failure(
                listOf(
                    ErrorMessage.UnhandledError
                )
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateAnonymousUserInfo(
        systemUid: SystemUid,
        nickname: String,
        comment: String
    ): Result<ShochuClubUserSummary.AnonymousUser> {
        return kotlin.runCatching {
            val user = accountController.updateAnonymousUserInfo(
                systemUid = systemUid.uid,
                nickname = nickname,
                comment = comment
            )
            return@runCatching Result.Success(
                ShochuClubUserSummary.AnonymousUser(
                    shochuClubUserId = ShochuClubUserId(value = user.systemUid),
                    nickname = user.nickName,
                    iconUrl = user.iconUrl,
                    registeredAt = LocalDateTime.now().toKotlinLocalDateTime()
                )
            )
        }.getOrElse {
            println(it)
            Result.Failure(
                listOf(
                    ErrorMessage.UnhandledError
                )
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateUserName(
        systemUid: SystemUid,
        username: String
    ): Result<ShochuClubUserSummary> {

        return kotlin.runCatching {
            val user = accountController.updateUserName(
                systemUid = systemUid.uid,
                username = username
            )
            return@runCatching Result.Success(
                ShochuClubUserSummary.AnonymousUser(
                    shochuClubUserId = ShochuClubUserId(value = user.systemUid),
                    nickname = user.nickName,
                    iconUrl = user.iconUrl,
                    registeredAt = LocalDateTime.now().toKotlinLocalDateTime()
                )
            )
        }.getOrElse {
            println(it)
            Result.Failure(
                listOf(
                    ErrorMessage.UnhandledError
                )
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun promoteProvisionedAccount(
        email: String,
        passwordRaw: String,
        confirmationCode: String
    ): Result<ShochuClubUserSummary.AuthenticatedUser> {
        return kotlin.runCatching {
            val user = accountController.promoteProvisionedAccount(
                email = email,
                passwordRaw = passwordRaw,
                confirmationCode = confirmationCode
            )

            return@runCatching Result.Success(
                ShochuClubUserSummary.AuthenticatedUser(
                    shochuClubUserId = ShochuClubUserId(value = user.systemUid),
                    iconUrl = user.iconUrl,
                    registeredAt = LocalDateTime.now().toKotlinLocalDateTime(),
                    nickname = user.nickName
                )
            )
        }.getOrElse {
            Result.Failure(
                listOf(ErrorMessage.UnhandledError)
            )
        }
    }
}
