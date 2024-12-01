package com.milkcocoa.info.shochu_club.server.infra.database.entities

import com.milkcocoa.info.shochu_club.server.infra.database.tables.shochu_brand.shochu_brand
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ShochuBrand(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ShochuBrand>(shochu_brand)

    var brandName by shochu_brand.brandName
    var brandDescription by shochu_brand.brandDescription
    var brandMainImageUrl by shochu_brand.brandMainImageUrl
    var makerId by ShochuMaker referencedOn shochu_brand.maker
    var createdAt by shochu_brand.createdAt
    var updatedAt by shochu_brand.updatedAt
}