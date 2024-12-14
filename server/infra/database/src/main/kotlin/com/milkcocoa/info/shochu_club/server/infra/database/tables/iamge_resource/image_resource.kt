package com.milkcocoa.info.shochu_club.server.infra.database.tables.iamge_resource

import com.milkcocoa.info.shochu_club.server.infra.database.column.ValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.column.stringValueObject
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

class ResourceUrl(value: String): ValueObject<String>(value)


object image_resource: UUIDTable("image_resource", columnName = "resource_id") {
    val resourceUrl = stringValueObject<ResourceUrl>("resource_url")
    val createdAt = timestampWithTimeZone("created_at")
    val updatedAt = timestampWithTimeZone("updated_at")
}