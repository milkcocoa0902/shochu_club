package com.milkcocoa.info.shochu_club.models.feed

import com.milkcocoa.info.shochu_club.models.details.ResponseDataEntity
import com.milkcocoa.info.shochu_club.models.user.ShochuClubUserSummary
import com.milkcocoa.info.shochu_club.serializers.UuidSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
sealed class MediaResolution {
    @Serializable
    data object Original : MediaResolution()
    @Serializable
    data object HiRes : MediaResolution()
    @Serializable
    data object MidRes : MediaResolution()
    @Serializable
    data object LowRes : MediaResolution()
}


@Serializable
sealed class MediaEntity{
    @Serializable
    data object NoData: MediaEntity()
    @Serializable
    data object Broken : MediaEntity()

    @Serializable
    data class Image(
        val url: String,
        val resolution: MediaResolution,
    ): MediaEntity()

    @Serializable
    data class Video(
        val url: String,
        val thumbnail: List<Image>
    ): MediaEntity()
}

/**
 * 投稿のサマリオブジェクト
 */
@OptIn(ExperimentalUuidApi::class)
@Serializable
sealed class FeedSummaryRecord(
    @Serializable(with = UuidSerializer::class)
    val id: Uuid
): ResponseDataEntity{
    @Serializable
    data class NormalFeedEntity(
        /**
         * 投稿ID
         */
        val shochuFeedId: ShochuFeedId,
        /**
         * 投稿内容
         */
        val text: String,
        /**
         * 投稿した人。正会員のみ
         */
        val postOwner: ShochuClubUserSummary.AuthenticatedUser,
        /**
         * ブランド
         */
        val shochuBrand: ShochuBrandSummary,
        /**
         * 画像URL
         */
        val medias: List<MediaEntity>,
        /**
         * 投稿日時
         */
        val postAt: Instant,
    ): FeedSummaryRecord(shochuFeedId.value)
}

/**
 * 投稿の詳細オブジェクト
 */
@Serializable
sealed class FeedDetail : ResponseDataEntity{
    @Serializable
    data class NormalFeed(
        /**
         * 投稿ID
         */
        val shochuFeedId: ShochuFeedId,
        /**
         * 投稿内容
         */
        val text: String,
        /**
         * 投稿日時
         */
        val postOwner: ShochuClubUserSummary.AuthenticatedUser,
        /**
         * ブランド
         */
        val shochuBrand: ShochuBrandDetail,
        /**
         * 画像URL
         */
        val medias: List<MediaEntity>,
        /**
         * 投稿日時
         */
        val postAt: Instant,
    ): FeedDetail()
}
