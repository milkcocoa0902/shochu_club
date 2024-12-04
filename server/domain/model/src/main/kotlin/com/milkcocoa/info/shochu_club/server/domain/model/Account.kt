package com.milkcocoa.info.shochu_club.server.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


sealed class Account{
    @OptIn(ExperimentalUuidApi::class)
    data class AnonymousUser(
        /**
         * システム上のID
         */
        val systemUid: Uuid,
        /**
         * ユーザ名。Twitterでいう@以降
         */
        val userName: String,
        /**
         * スクリーンネーム
         */
        val nickName: String,
        /**
         * アイコン画像URL
         */
        val iconUrl: String,

        val comment: String,
    ): Account()


    @OptIn(ExperimentalUuidApi::class)
    data class ProvisionedUser(
        val provisionedUserId: Uuid,
        val systemUid: Uuid?,
        val email: String,
        val passwordHash: String,
    ): Account()

    @OptIn(ExperimentalUuidApi::class)
    data class AuthenticatedUser(
        /**
         * システム上のID
         */
        val systemUid: Uuid,
        /**
         * ユーザ名。Twitterでいう@以降
         */
        val userName: String,
        /**
         * スクリーンネーム
         */
        val nickName: String,
        /**
         * アイコン画像URL
         */
        val iconUrl: String,

        val comment: String,
    ): Account()
}
