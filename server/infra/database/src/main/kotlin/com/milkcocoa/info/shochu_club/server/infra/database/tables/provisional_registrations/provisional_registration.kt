package com.milkcocoa.info.shochu_club.server.infra.database.tables.provisional_registrations

import com.milkcocoa.info.shochu_club.server.infra.database.column.integerValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.column.stringValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.tables.common.AuthProvider
import com.milkcocoa.info.shochu_club.server.infra.database.tables.common.Email
import com.milkcocoa.info.shochu_club.server.infra.database.tables.common.PasswordHash
import com.milkcocoa.info.shochu_club.server.infra.database.tables.system_uid.system_uid
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object provisional_registration: UUIDTable("provisional_registration", "id") {
    val uid = reference(
        "system_uid",
        system_uid,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )

    val email = stringValueObject<Email>("email")
    val password_hash = stringValueObject<PasswordHash>("password_hash")
    val registration_type = integerValueObject<RegistrationType>("registration_type")
    val created_at = datetime("created_at")

}