package com.milkcocoa.info.shochu_club.server.infra.database.entities

import com.milkcocoa.info.shochu_club.server.infra.database.tables.shochu_maker.shochu_maker
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ShochuMaker(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<ShochuMaker>(shochu_maker)

    var makerName by shochu_maker.makerName
    var makerDescription by shochu_maker.makerDescription
    var makerUrl by shochu_maker.makerUrl
    var makerAddress by shochu_maker.makerAddress
    var makerArea by shochu_maker.makerArea
    var createdAt by shochu_maker.createdAt
    var updatedAt by shochu_maker.updatedAt
}