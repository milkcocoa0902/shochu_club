package com.milkcocoa.info.shochu_club.server.infra.database.entities

import com.milkcocoa.info.shochu_club.server.infra.database.tables.iamge_resource.image_resource
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ImageResource(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object: UUIDEntityClass<ImageResource>(image_resource)

    var resourceUrl by image_resource.resourceUrl
    var createdAt by image_resource.createdAt
    var updatedAt by image_resource.updatedAt
}