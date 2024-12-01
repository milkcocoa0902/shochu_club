package com.milkcocoa.info.shochu_club.models.user

import com.milkcocoa.info.shochu_club.models.details.ResponseDataEntity
import com.milkcocoa.info.shochu_club.serializers.UuidSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * アカウントのサマリ。
 * このオブジェクトは匿名でも正会員であっても成立する
 */


@Serializable
sealed class ShochuClubUserSummary: ResponseDataEntity{
    @OptIn(ExperimentalSerializationApi::class)
    @Serializable
    data class AnonymousUser(
        /**
         * システム上のユーザIDを識別するための一意なID
         */
        @ProtoNumber(number = 1)
        val shochuClubUserId: ShochuClubUserId,
        /**
         * ニックネーム
         */
        @ProtoNumber(number = 2)
        val nickname: String,

        /**
         * アイコン画像URL
         */
        @ProtoNumber(number = 3)
        val iconUrl: String,

        /**
         * サービス登録日時
         */
        @ProtoNumber(number = 5)
        val registeredAt: LocalDateTime,
    ) : ShochuClubUserSummary(){
        init {

        }
    }


    @OptIn(ExperimentalSerializationApi::class)
    @Serializable
    data class AuthenticatedUser(
        /**
         * システム上のユーザIDを識別するための一意なID
         */
        @ProtoNumber(number = 1)
        val shochuClubUserId: ShochuClubUserId,
        /**
         * ニックネーム
         */
        @ProtoNumber(number = 2)
        val nickname: String,

        /**
         * アイコン画像URL
         */
        @ProtoNumber(number = 3)
        val iconUrl: String,

        /**
         * サービス登録日時
         */
        @ProtoNumber(number = 5)
        val registeredAt: LocalDateTime,
    ) : ShochuClubUserSummary()

    @OptIn(ExperimentalUuidApi::class, ExperimentalSerializationApi::class)
    @Serializable
    data class ProvisionedUser(
        @Serializable(UuidSerializer::class)
        @ProtoNumber(number = 1)
        val provisionedUserId: Uuid,
    ): ShochuClubUserSummary()
}