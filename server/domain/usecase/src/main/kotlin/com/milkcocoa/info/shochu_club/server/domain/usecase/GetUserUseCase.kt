package com.milkcocoa.info.shochu_club.server.domain.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.model.User

interface GetUserUseCase {
    suspend fun findByUid(account: Account): User
}
