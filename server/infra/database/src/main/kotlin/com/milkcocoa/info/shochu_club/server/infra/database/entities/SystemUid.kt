package com.milkcocoa.info.shochu_club.server.infra.database.entities

import com.milkcocoa.info.shochu_club.server.infra.database.tables.anonymous_user.anonymous_user
import com.milkcocoa.info.shochu_club.server.infra.database.tables.relationship.relationship_table
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

    val anon by AnonymousUser referrersOn anonymous_user.uid

    val followed by Relationship referrersOn relationship_table.follower
    val follows by Relationship referrersOn relationship_table.followee
}