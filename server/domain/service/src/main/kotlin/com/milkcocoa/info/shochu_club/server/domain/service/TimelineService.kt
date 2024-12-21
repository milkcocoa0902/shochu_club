package com.milkcocoa.info.shochu_club.server.domain.service

import com.milkcocoa.info.shochu_club.server.domain.model.feed.FeedSummaryDataObject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface TimelineService {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun homeTimeline(uid: Uuid, cursor: String?, count: Int): List<FeedSummaryDataObject>
}