package com.milkcocoa.info.shochu_club.models.feed

import com.milkcocoa.info.shochu_club.models.details.ResponseDataEntity
import kotlinx.serialization.Serializable

@Serializable
data class ShochuMakerSummary(
    val makerId: ShochuMakerId,
    val makerName: String,
    val makerUrl: String,
    val makerArea: Int,
    val makerImageUrl: String,
) : ResponseDataEntity

@Serializable
data class ShochuMakerDetail(
    val makerId: ShochuMakerId,
    val makerName: String,
    val makerDescription: String,
    val makerUrl: String,
    val makerAddress: String,
    val makerArea: Int,
    val makerAreaForDisplay: String,
    val makerImageUrl: String,
) : ResponseDataEntity
