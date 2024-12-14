package com.milkcocoa.info.shochu_club.server.infra.database.tables.system_uid

import com.milkcocoa.info.evessa_fan_app.server.infra.database.column.booleanValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.column.integerValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.column.stringValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.tables.common.DeleteReason
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

object system_uid: UUIDTable("system_uid", "uid") {
    val username = stringValueObject<UserName>("username")
    val is_anonymous_user = booleanValueObject<IsAnonymousUser>("is_anonymous_user")
    val is_deleted = booleanValueObject<IsDeleted>("is_deleted")
    val deleteReason = integerValueObject<DeleteReason>("delete_reason").nullable()

    val createdAt = timestampWithTimeZone("created_at")
    val deletedAt = timestampWithTimeZone("deleted_at").nullable()
}