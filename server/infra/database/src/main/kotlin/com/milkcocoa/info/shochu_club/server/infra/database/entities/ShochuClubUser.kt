package com.milkcocoa.info.shochu_club.server.infra.database.entities

import com.milkcocoa.info.shochu_club.server.infra.database.tables.sohchu_club_user.shochu_club_user
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ShochuClubUser(
    id: EntityID<UUID>,
) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ShochuClubUser>(shochu_club_user)
    var systemUid by SystemUid referencedOn shochu_club_user.uid
    var email by shochu_club_user.email
    var isEmailVerified by shochu_club_user.isEmailVerified
    var passwordHash by shochu_club_user.passwordHash
    var passwordChangedAt by shochu_club_user.passwordChangedAt
    var failedLoginAttempts by shochu_club_user.failedLoginAttempts
    var authProvider by shochu_club_user.authProvider
    var isEnabled by shochu_club_user.isEnabled
    var isDeleted by shochu_club_user.isDeleted
    var isLocked by shochu_club_user.isLocked
    var multiFactorAuthEnabled by shochu_club_user.multiFactorAuthEnabled
    var multiFactorAuthSecret by shochu_club_user.multiFactorAuthSecret
    var birthday by shochu_club_user.birthday
    var nickName by shochu_club_user.nickname
    var comment by shochu_club_user.comment
    var profileIconUrl by shochu_club_user.profileIconUrl
    var createdAt by shochu_club_user.createdAt
    var updatedAt by shochu_club_user.updatedAt
    var lastLoginAt by shochu_club_user.lastLoginAt
    var accountDeletedAt by shochu_club_user.accountDeletedAt
    var accountDisabledAt by shochu_club_user.accountDisabledAt
}
