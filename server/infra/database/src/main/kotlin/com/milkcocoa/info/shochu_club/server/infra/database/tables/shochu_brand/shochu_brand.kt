package com.milkcocoa.info.shochu_club.server.infra.database.tables.shochu_brand

import com.milkcocoa.info.shochu_club.server.infra.database.column.ValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.column.stringValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.tables.iamge_resource.image_resource
import com.milkcocoa.info.shochu_club.server.infra.database.tables.shochu_maker.shochu_maker
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

class BrandName(value: String): ValueObject<String>(value)

class BrandDescription(value: String): ValueObject<String>(value)

object shochu_brand: UUIDTable("shochu_brand", "brand_id") {
    val brandName = stringValueObject<BrandName>("brand_name")
    val brandDescription = stringValueObject<BrandDescription>("brand_description")
    val brandMainImageUrl = reference(
        "brand_main_image_url",
        image_resource,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.SET_NULL
    ).nullable()
    val maker = reference(
        "maker_id",
        shochu_maker,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )

    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}