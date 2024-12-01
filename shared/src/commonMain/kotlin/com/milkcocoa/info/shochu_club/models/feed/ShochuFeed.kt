package com.milkcocoa.info.shochu_club.models.feed

import com.milkcocoa.info.shochu_club.models.details.ResponseDataEntity
import com.milkcocoa.info.shochu_club.models.user.ShochuClubUserDetail
import com.milkcocoa.info.shochu_club.models.user.ShochuClubUserSummary
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ShochuFeedSummary(
    val shochuFeedId: ShochuFeedId,
    val text: String,
    val postOwner: ShochuClubUserSummary,
    val shochuBrand: ShochuBrandSummary,
    val imageUrl: List<String>,
    val postAt: LocalDateTime,
) : ResponseDataEntity

@Serializable
data class ShochuFeedDetail(
    val shochuFeedId: ShochuFeedId,
    val text: String,
    val postOwner: ShochuClubUserDetail,
    val shochuBrand: ShochuBrandDetail,
    val imageUrl: List<String>,
    val postAt: LocalDateTime,
) : ResponseDataEntity
