package com.milkcocoa.info.shochu_club.server.infra.database.entities

import com.milkcocoa.info.shochu_club.server.infra.database.tables.user_session.user_session
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class UserSession(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserSession>(user_session)

    var systemUid by SystemUid referencedOn user_session.uid
    var refreshTokenHash by user_session.refresh_token_hash
    var createdAt by user_session.created_at
    var updatedAt by user_session.updated_at
    var expiresAt by user_session.expires_at
}