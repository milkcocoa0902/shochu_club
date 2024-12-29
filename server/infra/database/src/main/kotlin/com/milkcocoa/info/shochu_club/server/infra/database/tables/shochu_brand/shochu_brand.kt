package com.milkcocoa.info.shochu_club.server.infra.database.tables.shochu_brand

import com.milkcocoa.info.shochu_club.server.infra.database.column.ValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.column.stringValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.tables.common.ResourceUrl
import com.milkcocoa.info.shochu_club.server.infra.database.tables.shochu_maker.shochu_maker
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

class BrandName(value: String): ValueObject<String>(value)

class BrandDescription(value: String): ValueObject<String>(value)

object shochu_brand: UUIDTable("shochu_brand", "brand_id") {
    val brandName = stringValueObject<BrandName>("brand_name")
    val brandDescription = stringValueObject<BrandDescription>("brand_description")
    val maker = reference(
        "maker_id",
        shochu_maker,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )

    val createdAt = timestampWithTimeZone("created_at")
    val updatedAt = timestampWithTimeZone("updated_at")
}



object shochu_brand_image_resource: UUIDTable("shochu_brand_image_resource", "resource_id") {
    val resourceUrl = stringValueObject<ResourceUrl>("resource_url")
    val relatedBrand = reference(
        "related_brand",
        shochu_brand,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val createdAt = timestampWithTimeZone("created_at")
}