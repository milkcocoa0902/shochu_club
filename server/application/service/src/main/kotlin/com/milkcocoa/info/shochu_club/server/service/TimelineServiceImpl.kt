package com.milkcocoa.info.shochu_club.server.service

import com.milkcocoa.info.shochu_club.server.domain.model.StoredMediaObject
import com.milkcocoa.info.shochu_club.server.domain.model.feed.FeedSummaryDataObject
import com.milkcocoa.info.shochu_club.server.domain.repository.DistributionBackend
import com.milkcocoa.info.shochu_club.server.domain.repository.FeedRepository
import com.milkcocoa.info.shochu_club.server.domain.service.TimelineService
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class TimelineServiceImpl(
    private val feedRepository: FeedRepository,
    private val distributionBackend: DistributionBackend
): TimelineService {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun homeTimeline(
        uid: Uuid,
        cursor: String?,
        count: Int
    ): List<FeedSummaryDataObject> {
        return newSuspendedTransaction {
            return@newSuspendedTransaction feedRepository.homeTimeline(
                uid = uid,
                cursor = cursor,
                count = count
            ).map { feed ->
                when(feed){
                    is FeedSummaryDataObject.NormalFeedData -> {
                        feed.copy(
                            owner = feed.owner.copy(
                                iconUrl = when(feed.owner.iconUrl){
                                    is StoredMediaObject.Image.UnResolved ->{
                                        StoredMediaObject.Image.Resolved(
                                            url = distributionBackend.generateSignedUrl(
                                                key = (feed.owner.iconUrl as StoredMediaObject.Image.UnResolved).key,
                                                expires = 2000L
                                            ),
                                            resolution = (feed.owner.iconUrl as StoredMediaObject.Image.UnResolved).resolution
                                        )
                                    }
                                    else -> feed.owner.iconUrl
                                }
                            ),
                            storedMediaObjects = feed.storedMediaObjects.map { media ->
                                when(media){
                                    is StoredMediaObject.Image.UnResolved ->{
                                        StoredMediaObject.Image.Resolved(
                                            url = distributionBackend.generateSignedUrl(
                                                key = media.key,
                                                expires = 2000L
                                            ),
                                            resolution = media.resolution
                                        )
                                    }
                                    is StoredMediaObject.Image.Resolved ->{
                                        // 現実的にはResolvedの状態で入ってくることはないはず。
                                        // Repositoryレイヤでは画像の解決はできないはずなので。
                                        media
                                    }
                                    is StoredMediaObject.Image.NoData ->{
                                        media
                                    }
                                    is StoredMediaObject.Video.UnResolved ->{
                                        StoredMediaObject.Video.Resolved(
                                            url = distributionBackend.generateSignedUrl(
                                                key = "",
                                                expires = 2000L
                                            ),
                                            thumbnails = media.thumbnails.map { thumbs ->
                                                StoredMediaObject.Image.Resolved(
                                                    url = distributionBackend.generateSignedUrl(
                                                        key = "",
                                                        expires = 2000L
                                                    ),
                                                    resolution = thumbs.resolution
                                                )
                                            }
                                        )
                                    }
                                    is StoredMediaObject.Video.Resolved ->{
                                        // 画像と同じく、現実的にはResolvedの状態で入ってくることはないはず。
                                        // Repositoryレイヤで動画の解決はできないはずなので。
                                        media
                                    }
                                }
                            }
                        )
                    }
                }
            }.toList()
        }
    }
}