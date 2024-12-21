package com.milkcocoa.info.shochu_club.models.feed

import com.milkcocoa.info.shochu_club.serializers.UuidSerializer
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@JvmInline
@OptIn(ExperimentalUuidApi::class)
@Serializable
value class ShochuBrandId(
    @Serializable(UuidSerializer::class)
    val value: Uuid,
)
