package com.milkcocoa.info.shochu_club.models.user


import com.milkcocoa.info.shochu_club.serializers.UuidSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalSerializationApi::class)
@Serializable
data class ShochuClubUserId(
    @Serializable(UuidSerializer::class)
    @ProtoNumber(1)
    val value: Uuid,
)
