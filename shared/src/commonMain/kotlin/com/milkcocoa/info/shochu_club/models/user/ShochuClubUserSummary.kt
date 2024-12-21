package com.milkcocoa.info.shochu_club.models.user

import com.milkcocoa.info.shochu_club.models.details.ResponseDataEntity
import com.milkcocoa.info.shochu_club.serializers.UuidSerializer
import kotlinx.datetime.Instant
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
sealed class ShochuClubUserSummary: ResponseDataEntity {
    @OptIn(ExperimentalSerializationApi::class)
    @Serializable
    data class AnonymousUser(
        /**
         * システム上のユーザIDを識別するための一意なID
         */
        @ProtoNumber(number = 1)
        val shochuClubUserId: ShochuClubUserId,

        /**
         * ユーザIDに対応するID。システム上で一意。
         */
        @ProtoNumber(number = 2)
        val username: String,

        /**
         * ニックネーム
         */
        @ProtoNumber(number = 3)
        val nickname: String,

        /**
         * アイコン画像URL
         */
        @ProtoNumber(number = 4)
        val iconUrl: String,

        /**
         * サービス登録日時
         */
        @ProtoNumber(number = 5)
        val registeredAt: Instant,
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
         * ユーザIDに対応するID。システム上で一意。
         */
        @ProtoNumber(number = 2)
        val username: String,

        /**
         * ニックネーム
         */
        @ProtoNumber(number = 3)
        val nickname: String,

        /**
         * アイコン画像URL
         */
        @ProtoNumber(number = 4)
        val iconUrl: String,

        /**
         * サービス登録日時
         */
        @ProtoNumber(number = 5)
        val registeredAt: Instant,
    ) : ShochuClubUserSummary()

    @OptIn(ExperimentalUuidApi::class, ExperimentalSerializationApi::class)
    @Serializable
    data class ProvisionedUser(
        @Serializable(UuidSerializer::class)
        @ProtoNumber(number = 1)
        val provisionedUserId: Uuid,
    ): ShochuClubUserSummary()
}