package com.milkcocoa.info.shochu_club.server.infra.database.entities

import com.milkcocoa.info.shochu_club.server.infra.database.tables.ShochuClubUserId
import com.milkcocoa.info.shochu_club.server.infra.database.tables.shochu_club_user
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ShochuClubUser(
    id: EntityID<ShochuClubUserId>,
) : Entity<ShochuClubUserId>(id) {
    companion object : EntityClass<ShochuClubUserId, ShochuClubUser>(shochu_club_user)

    var birthday by shochu_club_user.birthday
    var nickName by shochu_club_user.nickname
    var firebaseUid by shochu_club_user.firebase_uid
    var comment by shochu_club_user.comment
    var iconUrl by shochu_club_user.icon_url
    var createdAt by shochu_club_user.created_at
    var updatedAt by shochu_club_user.updated_at
}
