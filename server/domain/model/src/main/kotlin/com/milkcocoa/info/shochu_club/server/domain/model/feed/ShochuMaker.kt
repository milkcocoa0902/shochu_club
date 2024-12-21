package com.milkcocoa.info.shochu_club.server.domain.model.feed

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
data class ShochuMakerSummary(
    val makerId: Uuid
)

@OptIn(ExperimentalUuidApi::class)
data class ShochuMakerDetails(
    val makerId: Uuid,
)