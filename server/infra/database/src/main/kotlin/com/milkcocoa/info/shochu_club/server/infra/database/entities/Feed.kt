package com.milkcocoa.info.shochu_club.server.infra.database.entities

import com.milkcocoa.info.shochu_club.server.infra.database.tables.feed.feed_table
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class Feed(
    id: EntityID<UUID>
): UUIDEntity(id) {
    companion object : UUIDEntityClass<Feed>(feed_table)

    var text by feed_table.text
    var postOwner by SystemUid referencedOn feed_table.postOwner
    var brand by ShochuBrand referencedOn feed_table.brand
    var feedCategory by feed_table.feedCategory
    var createdAt by feed_table.createdAt
    var updatedAt by feed_table.updatedAt
}