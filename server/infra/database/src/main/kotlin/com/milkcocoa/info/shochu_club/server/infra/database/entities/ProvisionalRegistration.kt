package com.milkcocoa.info.shochu_club.server.infra.database.entities

import com.milkcocoa.info.shochu_club.server.infra.database.tables.provisional_registrations.provisional_registration
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class ProvisionalRegistration(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<ProvisionalRegistration>(provisional_registration)

    var systemUid by SystemUid referencedOn provisional_registration.uid
    var email by provisional_registration.email
    var passwordHash by provisional_registration.password_hash
    var registrationType by provisional_registration.registration_type
    var createdAt by provisional_registration.created_at
}