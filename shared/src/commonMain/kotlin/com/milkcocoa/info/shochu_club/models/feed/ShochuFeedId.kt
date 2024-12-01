package com.milkcocoa.info.shochu_club.models.feed

import com.milkcocoa.info.shochu_club.serializers.UuidSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class ShochuFeedId(
    @Serializable(UuidSerializer::class)
    val value: Uuid,
)
