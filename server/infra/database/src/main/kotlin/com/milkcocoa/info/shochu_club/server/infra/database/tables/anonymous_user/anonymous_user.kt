package com.milkcocoa.info.shochu_club.server.infra.database.tables.anonymous_user

import com.milkcocoa.info.shochu_club.server.infra.database.column.integerValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.column.stringValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.tables.common.Comment
import com.milkcocoa.info.shochu_club.server.infra.database.tables.common.DeleteReason
import com.milkcocoa.info.shochu_club.server.infra.database.tables.common.NickName
import com.milkcocoa.info.shochu_club.server.infra.database.tables.system_uid.system_uid
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

object anonymous_user: UUIDTable("anonymous_user", "id") {
    val uid = reference(
        "system_uid",
        system_uid,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val nickname = stringValueObject<NickName>("nickname")
    val comment = stringValueObject<Comment>("comment")
    val delete_reason = integerValueObject<DeleteReason>("delete_reason").nullable()
    val createdAt = timestampWithTimeZone("created_at")
    val updatedAt = timestampWithTimeZone("updated_at")
    val deletedAt = timestampWithTimeZone("deleted_at").nullable()

}