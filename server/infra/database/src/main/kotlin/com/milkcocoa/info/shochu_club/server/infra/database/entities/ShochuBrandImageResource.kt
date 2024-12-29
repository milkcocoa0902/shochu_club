package com.milkcocoa.info.shochu_club.server.infra.database.entities

import com.milkcocoa.info.shochu_club.server.infra.database.tables.shochu_brand.shochu_brand_image_resource
import com.milkcocoa.info.shochu_club.server.infra.database.tables.shochu_maker.shochu_maker_image_resource
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ShochuBrandImageResource(
    id: EntityID<UUID>,
): UUIDEntity(id) {
    companion object : UUIDEntityClass<ShochuBrandImageResource>(shochu_brand_image_resource)

    var resourceUrl by shochu_maker_image_resource.resourceUrl
    var relatedBrand by ShochuBrand referencedOn shochu_brand_image_resource.relatedBrand
    var createdAt by shochu_maker_image_resource.createdAt
}