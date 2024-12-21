package com.milkcocoa.info.shochu_club.server.infra.database.tables.profile_image_resource

import com.milkcocoa.info.shochu_club.server.infra.database.column.stringValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.tables.common.ResourceUrl
import com.milkcocoa.info.shochu_club.server.infra.database.tables.system_uid.system_uid
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone


object profile_image_resource: UUIDTable("image_resource", columnName = "resource_id") {
    val resourceUrl = stringValueObject<ResourceUrl>("resource_url")
    val resourceOwner = reference(
        "resource_owner",
        system_uid,
        onDelete = ReferenceOption.SET_NULL,
        onUpdate = ReferenceOption.CASCADE
    ).nullable()


    val createdAt = timestampWithTimeZone("created_at")
}