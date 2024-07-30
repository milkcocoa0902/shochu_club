package com.milkcocoa.info.shochu_club.server.domain.repository

import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.model.IdToken

interface AccountRepository {
    suspend fun signUp(credential: IdToken): Result<Account>

    suspend fun signIn(credential: IdToken): Result<Account>
}
