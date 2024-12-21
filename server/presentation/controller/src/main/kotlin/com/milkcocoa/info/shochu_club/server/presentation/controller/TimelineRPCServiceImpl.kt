package com.milkcocoa.info.shochu_club.server.presentation.controller

import com.milkcocoa.info.application.controller.TimelineController
import com.milkcocoa.info.shochu_club.models.details.ErrorMessage
import com.milkcocoa.info.shochu_club.models.details.Result
import com.milkcocoa.info.shochu_club.models.feed.*
import com.milkcocoa.info.shochu_club.models.user.ShochuClubUserId
import com.milkcocoa.info.shochu_club.models.user.ShochuClubUserSummary
import com.milkcocoa.info.shochu_club.net.rpc.TimelineRPCService
import com.milkcocoa.info.shochu_club.server.domain.model.MediaResolutionVariant
import com.milkcocoa.info.shochu_club.server.domain.model.StoredMediaObject
import com.milkcocoa.info.shochu_club.server.domain.model.feed.FeedSummaryDataObject
import kotlinx.datetime.toKotlinInstant
import kotlin.coroutines.CoroutineContext
import org.koin.java.KoinJavaComponent.inject
import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi

class TimelineRPCServiceImpl(
    override val coroutineContext: CoroutineContext
): TimelineRPCService {
    private val timelineController: TimelineController by inject(TimelineController::class.java)

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun homeTimeline(
        uid: ShochuClubUserId,
        cursor: String?,
        count: Int?,
    ): Result<Timeline> {
        return kotlin.runCatching {
            val timeline = timelineController.homeTimeline(
                uid = uid.value,
                cursor = cursor,
                count = count ?: 50
            )
            return@runCatching Result.Success(
                value = Timeline(
                    feeds = timeline.map { it.toFeedSummaryRecord() }
                )
            )
        }.getOrElse {
            Result.Failure(
                listOf(
                    ErrorMessage.UnhandledError
                )
            )
        }
    }

    override suspend fun userTimeline(userId: ShochuClubUserId): Result<Timeline> {
        TODO("Not yet implemented")
    }
}

fun MediaResolutionVariant.toResolution() = when (this) {
    is MediaResolutionVariant.Original -> MediaResolution.Original
    is MediaResolutionVariant.HiRes -> MediaResolution.HiRes
    is MediaResolutionVariant.MidRes -> MediaResolution.MidRes
    is MediaResolutionVariant.LowRes -> MediaResolution.LowRes
}

fun StoredMediaObject.toMediaEntity(): MediaEntity =
    when(this){
        is StoredMediaObject.Image ->{
            when(this){
                is StoredMediaObject.Image.NoData -> MediaEntity.NoData
                is StoredMediaObject.Image.UnResolved -> MediaEntity.Broken
                is StoredMediaObject.Image.Resolved -> MediaEntity.Image(
                    url = this.url,
                    resolution = this.resolution.toResolution()
                )
            }
        }
        is StoredMediaObject.Video ->{
            when(this){
                is StoredMediaObject.Video.Resolved -> MediaEntity.Video(
                    url = this.url,
                    thumbnail = this.thumbnails.map {
                        it.toMediaEntity()
                    }.filterIsInstance<MediaEntity.Image>()
                )
                is StoredMediaObject.Video.UnResolved -> MediaEntity.Broken
            }
        }
    }

@OptIn(ExperimentalUuidApi::class)
fun FeedSummaryDataObject.toFeedSummaryRecord() = when(this){
    is FeedSummaryDataObject.NormalFeedData ->{
        FeedSummaryRecord.NormalFeedEntity(
            shochuFeedId = ShochuFeedId(value = id),
            text = text,
            shochuBrand = ShochuBrandSummary(
                brandId = ShochuBrandId(brand.brandId),
                brandImageUrl = "",
                shochuMaker = ShochuMakerSummary(
                    makerId = ShochuMakerId(value = brand.makerId),
                    makerImageUrl = "",
                    makerName = "",
                    makerArea = 0,
                    makerUrl = ""
                ),
                brandName = ""
            ),
            postOwner = ShochuClubUserSummary.AuthenticatedUser(
                iconUrl = when(owner.iconUrl){
                    is StoredMediaObject.Image.Resolved -> (owner.iconUrl as StoredMediaObject.Image.Resolved).url
                    else -> ""
                },
                registeredAt = owner.registeredAt,
                shochuClubUserId = ShochuClubUserId(owner.systemUid),
                username = owner.userName,
                nickname = owner.nickName,
            ),
            postAt = ZonedDateTime.now().toInstant().toKotlinInstant(),
            medias = storedMediaObjects.map { it.toMediaEntity() }
        )
    }
}