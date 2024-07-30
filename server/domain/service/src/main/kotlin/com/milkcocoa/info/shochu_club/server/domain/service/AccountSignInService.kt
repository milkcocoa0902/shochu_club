package com.milkcocoa.info.shochu_club.server.domain.service

import com.milkcocoa.info.shochu_club.server.domain.model.IdToken
import com.milkcocoa.info.shochu_club.server.domain.model.User

interface AccountSignInService {
    suspend fun signIn(credential: IdToken): User
}
