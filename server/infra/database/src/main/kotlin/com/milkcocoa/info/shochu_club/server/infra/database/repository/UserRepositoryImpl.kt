package com.milkcocoa.info.shochu_club.server.infra.database.repository

import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.model.User
import com.milkcocoa.info.shochu_club.server.domain.repository.UserRepository
import com.milkcocoa.info.shochu_club.server.infra.database.entities.ShochuClubUser
import com.milkcocoa.info.shochu_club.server.infra.database.tables.FirebaseUid
import com.milkcocoa.info.shochu_club.server.infra.database.tables.shochu_club_user
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class UserRepositoryImpl : UserRepository {
    override suspend fun findUser(account: Account): User {
        val u = ShochuClubUser.find(shochu_club_user.firebase_uid eq FirebaseUid(account.uid)).first()

        return User(user_id = u.id.value.value)
    }
}
