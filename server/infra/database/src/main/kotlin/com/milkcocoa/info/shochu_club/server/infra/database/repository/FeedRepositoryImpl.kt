package com.milkcocoa.info.shochu_club.server.infra.database.repository

import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.model.MediaResolutionVariant
import com.milkcocoa.info.shochu_club.server.domain.model.StoredMediaObject
import com.milkcocoa.info.shochu_club.server.domain.model.feed.FeedSummaryDataObject
import com.milkcocoa.info.shochu_club.server.domain.model.feed.ShochuBrandSummary
import com.milkcocoa.info.shochu_club.server.domain.model.type.FeedCategoryType
import com.milkcocoa.info.shochu_club.server.domain.repository.FeedRepository
import com.milkcocoa.info.shochu_club.server.infra.database.entities.Feed
import com.milkcocoa.info.shochu_club.server.infra.database.entities.FeedImageResource
import com.milkcocoa.info.shochu_club.server.infra.database.entities.ShochuClubUser
import com.milkcocoa.info.shochu_club.server.infra.database.tables.feed.feed_table
import com.milkcocoa.info.shochu_club.server.infra.database.tables.feed_image_resource.feed_image_resource_table
import com.milkcocoa.info.shochu_club.server.infra.database.tables.profile_image_resource.profile_image_resource
import com.milkcocoa.info.shochu_club.server.infra.database.tables.relationship.relationship_table
import com.milkcocoa.info.shochu_club.server.infra.database.tables.shochu_brand.shochu_brand
import com.milkcocoa.info.shochu_club.server.infra.database.tables.shochu_maker.shochu_maker
import com.milkcocoa.info.shochu_club.server.infra.database.tables.sohchu_club_user.shochu_club_user
import com.milkcocoa.info.shochu_club.server.infra.database.tables.system_uid.system_uid
import kotlinx.datetime.toKotlinInstant
import org.jetbrains.exposed.sql.*
import java.time.OffsetDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

class FeedRepositoryImpl: FeedRepository {
    @OptIn(ExperimentalUuidApi::class)
    override fun homeTimeline(
        uid: Uuid,
        cursor: String?,
        count: Int,
    ): Iterable<FeedSummaryDataObject> {
        // 自分がフォローしている人たち
        val follows = system_uid.innerJoin(
            otherTable = relationship_table,
            onColumn = { system_uid.id },
            otherColumn = { relationship_table.followee }
        ).select(relationship_table.follower)

        // 投稿を検索する
        val currentFeed =
            feed_table.innerJoin(
                otherTable = system_uid,
                onColumn = { feed_table.postOwner },
                otherColumn = { system_uid.id },
                additionalConstraint = {
                    (system_uid.id eq uid.toJavaUuid()) or (system_uid.id inSubQuery follows)
                }
            ).innerJoin(
                otherTable = shochu_brand,
                otherColumn = { shochu_brand.id },
                onColumn = { feed_table.brand }
            ).innerJoin(
                otherTable = shochu_maker,
                otherColumn = { shochu_maker.id },
                onColumn = { shochu_brand.maker },
            ).selectAll()
                .where {
                    cursor?.let {
                        feed_table.createdAt.less(OffsetDateTime.parse(cursor))
                    } ?: Op.TRUE
                }
                .orderBy(feed_table.createdAt to SortOrder.DESC)
                .limit(n = count)
                .asSequence()
                .map { Feed.wrapRow(it) }

        // 投稿に関わったユーザを検索する
        val users =
            shochu_club_user.innerJoin(
                otherTable = system_uid,
                onColumn = { shochu_club_user.uid },
                otherColumn = { shochu_club_user.id },
                additionalConstraint = {
                    system_uid.id inList currentFeed.map { it.postOwner.id.value }.toList()
                }
            ).leftJoin(
                otherTable = profile_image_resource,
                onColumn = { shochu_club_user.iconUrl },
                otherColumn = { profile_image_resource.id }
            ).selectAll()
                .asSequence()
                .map { ShochuClubUser.wrapRow(it) }

        // 投稿に含まれている画像を検索する
        val imageMapping =
            feed_image_resource_table.innerJoin(
                otherTable = feed_table,
                onColumn = { feed_image_resource_table.relatedFeed },
                otherColumn = { feed_table.id },
                additionalConstraint = {
                    (feed_table.id inList currentFeed.map { it.id.value }.toList()) and
                            (feed_image_resource_table.relatedFeed neq null) and
                            (feed_image_resource_table.resourceOwner neq null)
                }
            ).selectAll().asSequence().map { FeedImageResource.wrapRow(it) }

        val userMap = users.associateBy { it.id.value }
        val imageMap = imageMapping.groupBy { it.relatedFeed!!.id.value }

        // 整形する
        return currentFeed.map { feed ->
            val u = userMap[feed.postOwner.id.value]!!
            val img = imageMap[feed.id.value] ?: emptyList()


            when(FeedCategoryType.valueOf(type = feed.feedCategory.value)){
                FeedCategoryType.NormalFeed ->{
                    FeedSummaryDataObject.NormalFeedData(
                        id = feed.id.value.toKotlinUuid(),
                        owner = Account.AuthenticatedUser(
                            systemUid = feed.postOwner.id.value.toKotlinUuid(),
                            iconUrl = u.iconUrl?.let {
                                StoredMediaObject.Image.UnResolved(
                                    id = it.id.value.toKotlinUuid(),
                                    key = it.resourceUrl.value,
                                    resolution = MediaResolutionVariant.Original
                                )
                            } ?: StoredMediaObject.Image.NoData,
                            registeredAt = feed.postOwner.createdAt.toInstant().toKotlinInstant(),
                            userName = feed.postOwner.username.value,
                            nickName = u.nickName.value,
                            comment = u.comment.value
                        ),
                        text = feed.text.value,
                        brand = ShochuBrandSummary(
                            makerId = feed.brand.makerId.id.value.toKotlinUuid(),
                            brandId = feed.brand.id.value.toKotlinUuid(),
                        ),
                        storedMediaObjects = img.map {
                            StoredMediaObject.Image.UnResolved(
                                id =it.id.value.toKotlinUuid(),
                                key = it.resourceUrl.value,
                                resolution = MediaResolutionVariant.Original
                            )
                        }
                    )
                }
            }
        }.asIterable()
    }
}