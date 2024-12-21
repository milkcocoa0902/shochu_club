package com.milkcocoa.info.shochu_club.server.domain.model.feed

import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.model.StoredMediaObject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

sealed class FeedSummaryDataObject{
    @OptIn(ExperimentalUuidApi::class)
    data class NormalFeedData(
        val id: Uuid,
        val owner: Account.AuthenticatedUser,
        val text: String,
        val brand: ShochuBrandSummary,
        val storedMediaObjects: List<StoredMediaObject>,
    ): FeedSummaryDataObject()
}