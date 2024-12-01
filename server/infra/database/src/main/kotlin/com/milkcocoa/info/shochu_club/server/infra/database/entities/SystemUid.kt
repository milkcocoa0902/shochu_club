package com.milkcocoa.info.shochu_club.server.infra.database.entities

import com.milkcocoa.info.shochu_club.server.infra.database.tables.system_uid.system_uid
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class SystemUid(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<SystemUid>(system_uid)

    var username by system_uid.username
    var createdAt by system_uid.createdAt
    var isDeleted by system_uid.is_deleted
    var isAnonymousUser by system_uid.is_anonymous_user
    var deleteReason by system_uid.deleteReason
    var deletedAt by system_uid.deletedAt
}