package com.milkcocoa.info.shochu_club.server.infra.database.tables.relationship

import com.milkcocoa.info.shochu_club.server.infra.database.tables.system_uid.system_uid
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

object relationship_table: UUIDTable("relationship", columnName = "relation_id") {
    val followee = reference(
        name = "followee",
        foreign = system_uid,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )

    val follower = reference(
        name = "follower",
        foreign = system_uid,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )

    val created_at = timestampWithTimeZone("created_at")
}