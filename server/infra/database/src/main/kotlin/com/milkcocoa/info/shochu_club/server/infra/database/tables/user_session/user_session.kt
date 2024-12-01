package com.milkcocoa.info.shochu_club.server.infra.database.tables.user_session

import com.milkcocoa.info.shochu_club.server.infra.database.column.stringValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.tables.system_uid.system_uid
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object user_session: UUIDTable("user_session", "session_id") {
    val uid = reference(
        "system_uid",
        system_uid,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )

    val refresh_token_hash = stringValueObject<RefreshTokenHash>("refresh_token_hash")
    val created_at =datetime("created_at")
    val updated_at =datetime("updated_at")
    val expires_at = datetime("expires_at")
}