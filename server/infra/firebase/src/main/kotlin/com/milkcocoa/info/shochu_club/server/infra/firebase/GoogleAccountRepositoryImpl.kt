package com.milkcocoa.info.shochu_club.server.infra.firebase

import com.google.firebase.auth.FirebaseAuth
import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.model.IdToken
import com.milkcocoa.info.shochu_club.server.domain.repository.AccountRepository

class GoogleAccountRepositoryImpl : AccountRepository {
    override suspend fun signUp(credential: IdToken): Result<Account> {
        val account = FirebaseAuth.getInstance().verifyIdToken(credential.value, true)

        return Result.success(
            Account(
                uid = account.uid,
            ),
        )
    }

    override suspend fun signIn(credential: IdToken): Result<Account> {
        val account = FirebaseAuth.getInstance().verifyIdToken(credential.value, true)

        return Result.success(
            Account(
                uid = account.uid,
            ),
        )
    }
}
