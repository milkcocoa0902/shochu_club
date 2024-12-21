package com.milkcocoa.info.shochu_club.models.feed

import com.milkcocoa.info.shochu_club.models.details.ResponseDataEntity
import kotlinx.serialization.Serializable

@Serializable
data class Timeline(
    val feeds: Iterable<FeedSummaryRecord>
): ResponseDataEntity
