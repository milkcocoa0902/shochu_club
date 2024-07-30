package com.milkcocoa.info.shochu_club.server.domain.repository

import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.model.User

interface UserRepository {
    suspend fun findUser(account: Account): User
}
