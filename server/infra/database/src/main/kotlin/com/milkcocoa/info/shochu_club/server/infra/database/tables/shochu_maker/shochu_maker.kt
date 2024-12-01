package com.milkcocoa.info.shochu_club.server.infra.database.tables.shochu_maker

import com.milkcocoa.info.shochu_club.server.infra.database.column.ValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.column.integerValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.column.stringValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.tables.iamge_resource.image_resource
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

class MakerName(value: String): ValueObject<String>(value)

class MakerDescription(value: String): ValueObject<String>(value)

class MakerUrl(value: String): ValueObject<String>(value)

class MakerAddress(value: String): ValueObject<String>(value)

class MakerArea(value: Int): ValueObject<Int>(value)


object shochu_maker: UUIDTable("shochu_maker", "maker_id") {
    val makerName = stringValueObject<MakerName>("maker_name")
    val makerDescription = stringValueObject<MakerDescription>("maker_description")
    val makerUrl = stringValueObject<MakerUrl>("maker_url")
    val makerAddress = stringValueObject<MakerAddress>("maker_address")
    val makerArea = integerValueObject<MakerArea>("maker_area")
    val mainMakerImageResource = reference(
        "main_maker_image_resource",
        image_resource,
        onUpdate = ReferenceOption.CASCADE,
        onDelete = ReferenceOption.SET_NULL,
    ).nullable()
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}