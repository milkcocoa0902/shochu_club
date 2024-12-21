package com.milkcocoa.info.shochu_club.server.infra.database.entities

import com.milkcocoa.info.shochu_club.server.infra.database.tables.relationship.relationship_table
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class Relationship(
    id: EntityID<UUID>
): UUIDEntity(id) {
    companion object: UUIDEntityClass<Relationship>(relationship_table)

    var followee by SystemUid referencedOn relationship_table.followee
    var follower by SystemUid referencedOn relationship_table.follower
    var createdAt by relationship_table.created_at
}