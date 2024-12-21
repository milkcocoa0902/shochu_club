package com.milkcocoa.info.shochu_club.server.infra.database.tables.feed

import com.milkcocoa.info.shochu_club.server.infra.database.column.integerValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.column.stringValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.tables.shochu_brand.shochu_brand
import com.milkcocoa.info.shochu_club.server.infra.database.tables.system_uid.system_uid
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

object feed_table: UUIDTable("feed", columnName = "record_id") {
    val text = stringValueObject<FeedText>("text")
    val postOwner = reference(
        "post_owner",
        system_uid,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val brand = reference(
        "brand_id",
        shochu_brand,
        onDelete = ReferenceOption.SET_NULL,
        onUpdate = ReferenceOption.CASCADE
    )

    val feedCategory = integerValueObject<FeedCategory>("feed_category")
    val createdAt = timestampWithTimeZone("created_at")
    val updatedAt = timestampWithTimeZone("updated_at")
}