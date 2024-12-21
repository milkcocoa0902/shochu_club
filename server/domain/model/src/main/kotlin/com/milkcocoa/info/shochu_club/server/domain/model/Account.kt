package com.milkcocoa.info.shochu_club.server.domain.model

import kotlinx.datetime.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

sealed class MediaResolutionVariant() {
    data object Original : MediaResolutionVariant()
    data object HiRes : MediaResolutionVariant()
    data object MidRes : MediaResolutionVariant()
    data object LowRes : MediaResolutionVariant()
}



sealed class StoredMediaObject{
    sealed class Image : StoredMediaObject() {
        data object NoData: Image()
        @OptIn(ExperimentalUuidApi::class)
        data class UnResolved(
            val id: Uuid,
            val key: String,
            val resolution: MediaResolutionVariant,
        ) : Image()

        data class Resolved(
            val url: String,
            val resolution: MediaResolutionVariant
        ) : Image()
    }

    sealed class Video : StoredMediaObject(){
        @OptIn(ExperimentalUuidApi::class)
        data class UnResolved(
            val id: Uuid,
            val key: String,
            val thumbnails: List<Image.UnResolved>
        ): Video()

        data class Resolved(
            val url: String,
            val thumbnails: List<Image.Resolved>
        ): Video()
    }
}

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
        val iconUrl: StoredMediaObject.Image,

        val comment: String,
        val registeredAt: Instant,
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
        val iconUrl: StoredMediaObject.Image,

        val comment: String,
        val registeredAt: Instant,
    ): Account()
}
