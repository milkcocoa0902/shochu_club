package com.milkcocoa.info.shochu_club.models.user

import com.milkcocoa.info.shochu_club.models.details.ResponseDataEntity
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber

/**
 * アカウント詳細。
 * このオブジェクトは正会員のみ
 */
@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class ShochuClubUserDetail(
    /**
     * システム上のユーザIDを識別するための一意なID
     */
    @ProtoNumber(number = 1)
    val shochuClubUserId: ShochuClubUserId,
    @ProtoNumber(number = 2)
    val username: String,
    @ProtoNumber(number = 3)
    val email: String,
    @ProtoNumber(number = 4)
    val isEmailVerified: Boolean,
    @ProtoNumber(number = 5)
    val changePasswordRequired: Boolean,
    @ProtoNumber(number = 6)
    val authProvider: Int,
    @ProtoNumber(number = 7)
    val multiFactorEnabled: Boolean,
    @ProtoNumber(number = 8)
    val birthDate: LocalDate,
    @ProtoNumber(number = 9)
    val nickname: String,
    @ProtoNumber(number = 10)
    val comment: String,
    @ProtoNumber(number = 11)
    val iconUrl: String,
    @ProtoNumber(number = 12)
    val registeredAt: LocalDateTime,
) : ResponseDataEntity