package com.milkcocoa.info.shochu_club.server.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.feed.FeedSummaryDataObject
import com.milkcocoa.info.shochu_club.server.domain.service.TimelineService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class HomeTimelineUseCaseImpl(
    private val timelineService: TimelineService,
): HomeTimelineUseCase {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun execute(
        uid: Uuid,
        cursor: String?,
        count: Int
    ): List<FeedSummaryDataObject> {
        return timelineService.homeTimeline(
            uid = uid,
            cursor = cursor,
            count = count
        )
    }
}