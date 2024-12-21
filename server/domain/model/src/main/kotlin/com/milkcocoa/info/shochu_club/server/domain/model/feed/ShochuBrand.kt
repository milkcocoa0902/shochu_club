package com.milkcocoa.info.shochu_club.server.domain.model.feed

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
data class ShochuBrandSummary(
    val brandId: Uuid,
    val makerId: Uuid,
)


@OptIn(ExperimentalUuidApi::class)
data class ShochuBrandDetails(
    val brandId: Uuid,
    val makerDetails: ShochuMakerDetails,
)