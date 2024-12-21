package com.milkcocoa.info.shochu_club.server.infra.database.entities

import com.milkcocoa.info.shochu_club.server.infra.database.tables.profile_image_resource.profile_image_resource
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ImageResource(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object: UUIDEntityClass<ImageResource>(profile_image_resource)

    var resourceUrl by profile_image_resource.resourceUrl
    var createdAt by profile_image_resource.createdAt
    val resourceOwner by SystemUid optionalReferencedOn profile_image_resource.resourceOwner
}