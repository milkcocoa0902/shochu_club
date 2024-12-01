package com.milkcocoa.info.shochu_club.server.infra.database.entities

import com.milkcocoa.info.shochu_club.server.infra.database.tables.anonymous_user.anonymous_user
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class AnonymousUser(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object: UUIDEntityClass<AnonymousUser>(anonymous_user)

    var systemUid by SystemUid referencedOn anonymous_user.uid
    var nickname by anonymous_user.nickname
    var comment by anonymous_user.comment
    var createdAt by anonymous_user.createdAt
    var updatedAt by anonymous_user.updatedAt
    var deletedAt by anonymous_user.deletedAt
    var deleteReason by anonymous_user.delete_reason
}