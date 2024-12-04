package com.milkcocoa.info.shochu_club.net.rpc

import com.milkcocoa.info.shochu_club.models.SystemUid
import com.milkcocoa.info.shochu_club.models.details.Result
import com.milkcocoa.info.shochu_club.models.user.ShochuClubUserSummary
import com.milkcocoa.info.shochu_club.net.client.ktorRpcClient
import kotlinx.rpc.RemoteService
import kotlinx.rpc.annotations.Rpc
import kotlinx.rpc.withService
import kotlin.uuid.ExperimentalUuidApi

@Rpc
interface AccountManagementRPCService : RemoteService {
    /**
     * システムでベアラートークンを発行する
     */
    suspend fun signInAnonymously(): Result<ShochuClubUserSummary.AnonymousUser>

    /**
     * 匿名ユーザのアカウント情報を更新する
     */
    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateAnonymousUserInfo(
        systemUid: SystemUid,
        nickname: String,
        comment: String
    ): Result<ShochuClubUserSummary.AnonymousUser>

    /**
     * Uidに紐づく情報＝ユーザIDを更新する
     */
    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateUserName(
        systemUid: SystemUid,
        username: String
    ): Result<ShochuClubUserSummary>

    /**
     * 匿名ユーザから、メアド&パスワードのアカウントの仮登録をする、
     * 仮登録とは、メールアドレスを検証する行為。つまり、SSOのユーザは対象外
     */
    suspend fun provisioningAnonymousAccount(
        systemUid: SystemUid,
        email: String,
        passwordRaw: String,
    ): Result<ShochuClubUserSummary.ProvisionedUser>

    /**
     * 仮登録アカウント=メアド&パスワードのアカウントを検証する。
     */
    suspend fun promoteProvisionedAccount(
        email: String,
        passwordRaw: String,
        confirmationCode: String
    ): Result<ShochuClubUserSummary.AuthenticatedUser>
}

suspend fun accountManagementRpcClient(urlString: String) = ktorRpcClient(urlString).withService<AccountManagementRPCService>()