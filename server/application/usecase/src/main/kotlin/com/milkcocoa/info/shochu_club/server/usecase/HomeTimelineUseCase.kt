package com.milkcocoa.info.shochu_club.server.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.feed.FeedSummaryDataObject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface HomeTimelineUseCase {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun execute(
        uid: Uuid,
        cursor: String?,
        count: Int,
    ): List<FeedSummaryDataObject>
}