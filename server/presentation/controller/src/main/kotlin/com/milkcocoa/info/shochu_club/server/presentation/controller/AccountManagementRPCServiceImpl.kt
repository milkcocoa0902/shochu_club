package com.milkcocoa.info.shochu_club.server.presentation.controller

import com.milkcocoa.info.application.controller.AccountController
import com.milkcocoa.info.shochu_club.models.SystemUid
import com.milkcocoa.info.shochu_club.models.details.ErrorMessage
import com.milkcocoa.info.shochu_club.models.details.Result
import com.milkcocoa.info.shochu_club.models.user.ShochuClubUserId
import com.milkcocoa.info.shochu_club.models.user.ShochuClubUserSummary
import com.milkcocoa.info.shochu_club.net.rpc.AccountManagementRPCService
import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.model.type.AuthProviderType
import kotlinx.datetime.toKotlinLocalDateTime
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDateTime
import kotlin.coroutines.CoroutineContext
import kotlin.uuid.ExperimentalUuidApi

class AccountManagementRPCServiceImpl(
    override val coroutineContext: CoroutineContext,
) : AccountManagementRPCService {
    private val accountController: AccountController by inject(AccountController::class.java)


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

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun provisioningAnonymousAccount(
        systemUid: SystemUid,
        email: String,
        passwordRaw: String,
    ): Result<ShochuClubUserSummary.ProvisionedUser> {
        return kotlin.runCatching {
            val user = accountController.provisioningAnonymousAccount(
                systemUid = systemUid.uid,
                email = email,
                passwordRaw = passwordRaw,
                authProvider = AuthProviderType.EmailAndPassword
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
            when(user){
                is Account.AnonymousUser ->{
                    return@runCatching Result.Success(
                        ShochuClubUserSummary.AnonymousUser(
                            shochuClubUserId = ShochuClubUserId(value = user.systemUid),
                            nickname = user.nickName,
                            iconUrl = user.iconUrl,
                            registeredAt = LocalDateTime.now().toKotlinLocalDateTime()
                        )
                    )
                }
                is Account.AuthenticatedUser ->{
                    return@runCatching Result.Success(
                        ShochuClubUserSummary.AuthenticatedUser(
                            shochuClubUserId = ShochuClubUserId(value = user.systemUid),
                            nickname = user.userName,
                            iconUrl = user.iconUrl,
                            registeredAt = LocalDateTime.now().toKotlinLocalDateTime()
                        )
                    )
                }
                is Account.ProvisionedUser ->{
                    error("")
                }
            }
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
