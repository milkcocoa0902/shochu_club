package com.milkcocoa.info.shochu_club.server.infra.database.entities

import com.milkcocoa.info.shochu_club.server.infra.database.tables.feed_image_resource.feed_image_resource_table
import com.milkcocoa.info.shochu_club.server.infra.database.tables.profile_image_resource.profile_image_resource
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class FeedImageResource(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object: UUIDEntityClass<FeedImageResource>(feed_image_resource_table)

    var resourceUrl by feed_image_resource_table.resourceUrl
    var createdAt by feed_image_resource_table.createdAt
    val resourceOwner by SystemUid optionalReferencedOn feed_image_resource_table.resourceOwner
    val relatedFeed by Feed optionalReferencedOn feed_image_resource_table.relatedFeed
}