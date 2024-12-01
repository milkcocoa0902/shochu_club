package com.milkcocoa.info.shochu_club.models.feed

import com.milkcocoa.info.shochu_club.models.details.ResponseDataEntity
import kotlinx.serialization.Serializable

@Serializable
data class ShochuBrandSummary(
    val brandId: ShochuBrandId,
    val brandName: String,
    val brandImageUrl: String,
    val shochuMaker: ShochuMakerSummary,
) : ResponseDataEntity

@Serializable
data class ShochuBrandDetail(
    val brandId: ShochuBrandId,
    val brandName: String,
    val brandDescription: String,
    val brandImageUrl: String,
    val brandSubImageUrl: List<String>,
    val shochuMaker: ShochuMakerDetail,
) : ResponseDataEntity
