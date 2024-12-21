package com.milkcocoa.info.application.controller

import com.milkcocoa.info.shochu_club.server.domain.model.feed.FeedSummaryDataObject
import com.milkcocoa.info.shochu_club.server.usecase.HomeTimelineUseCase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class TimelineControllerImpl(
    private val homeTimelineUseCase: HomeTimelineUseCase
) : TimelineController {
    @ExperimentalUuidApi
    override suspend fun homeTimeline(
        uid: Uuid,
        cursor: String?,
        count: Int
    ): List<FeedSummaryDataObject> {
        // TODO("本人しかリクエストできないようにする")

        return homeTimelineUseCase.execute(
            uid = uid,
            cursor = cursor,
            count = count
        )
    }
}