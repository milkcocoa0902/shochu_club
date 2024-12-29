package com.milkcocoa.info.shochu_club.server.infra.database.entities

import com.milkcocoa.info.shochu_club.server.infra.database.tables.shochu_maker.shochu_maker_image_resource
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class ShochuMakerImageResource(
    id: EntityID<UUID>
): UUIDEntity(id) {
    companion object: UUIDEntityClass<ShochuMakerImageResource>(shochu_maker_image_resource)

    var resourceUrl by shochu_maker_image_resource.resourceUrl
    var relatedMaker by ShochuMaker referencedOn shochu_maker_image_resource.relatedMaker
    var createdAt by shochu_maker_image_resource.createdAt
}