package com.milkcocoa.info.shochu_club.server.domain.repository

import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.model.type.AuthProviderType
import com.milkcocoa.info.shochu_club.server.domain.model.type.DeleteReasonValue
import java.util.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface AccountRepository {
    /**
     * 匿名ユーザ、正会員を通して一意なIDを発行する。
     *
     * @param anonymously[Boolean]: 匿名ユーザとして発行するかどうか
     * @return システム上のユーザを識別するための一意なID
     */
    @OptIn(ExperimentalUuidApi::class)
    suspend fun issueSystemUid(
        anonymously: Boolean,
    ): Uuid

    /**
     * 匿名ユーザをシステムに登録する
     *
     * @param systemUid[UUID] 匿名ユーザ、正会員を通して一意なID
     * @return [Account.AnonymousUser] 会員情報サマリ
     *
     */
    @OptIn(ExperimentalUuidApi::class)
    suspend fun createUserAnonymously(
        systemUid: Uuid,
    ): Account.AnonymousUser

    /**
     * 匿名ユーザの情報をアップデートする
     * @param systemUid[UUID] ユーザを識別するための一意なID
     * @param nickname[String] ニックネーム
     * @param username[String] ユーザ名（システムで一意）
     */
    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateUserInfoAnonymously(
        systemUid: Uuid,
        nickname: String,
        comment: String,
    ): Account.AnonymousUser

    @OptIn(ExperimentalUuidApi::class)
    suspend fun provisionalSignUpFromAnonymousUser(
        systemUid: Uuid,
        email: String,
        passwordHash: String,
        authProvider: AuthProviderType
    ): Account.ProvisionedUser

    @OptIn(ExperimentalUuidApi::class)
    suspend fun createAuthenticatedUser(
        systemUid: Uuid,
        email: String,
        passwordHash: String,
        authProvider: AuthProviderType
    ): Account.AuthenticatedUser

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateUsername(systemUid: Uuid, username: String): Unit

    @OptIn(ExperimentalUuidApi::class)
    suspend fun findUserInformationByUid(systemUid: Uuid): Account?

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateUserInformation(systemUid: Uuid, nickname: String, comment: String): Account

    suspend fun findProvisionedUserByEmail(email: String): Account.ProvisionedUser?

    @OptIn(ExperimentalUuidApi::class)
    suspend fun promoteSystemUidIntoAuthenticated(systemUid: Uuid): Unit

    suspend fun deleteProvisionedUserIfExists(email: String): Boolean

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteAnonymousUserLogically(
        systemUid: Uuid,
        deleteReason: DeleteReasonValue
    ): Boolean


    suspend fun checkUserExists(email: String): Boolean
}
