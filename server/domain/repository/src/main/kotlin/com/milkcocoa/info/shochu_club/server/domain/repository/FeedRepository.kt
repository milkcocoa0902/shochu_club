package com.milkcocoa.info.shochu_club.server.domain.repository

import com.milkcocoa.info.shochu_club.server.domain.model.feed.FeedSummaryDataObject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface FeedRepository {
    @OptIn(ExperimentalUuidApi::class)
    fun homeTimeline(
        uid: Uuid,
        cursor: String?,
        count: Int,
    ): Iterable<FeedSummaryDataObject>
}