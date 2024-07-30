package com.milkcocoa.info.shochu_club.server.infra.firebase

import com.google.firebase.auth.FirebaseAuth
import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.model.IdToken
import com.milkcocoa.info.shochu_club.server.domain.repository.AccountRepository

class EmailAccountRepositoryImpl : AccountRepository {
    override suspend fun signUp(credential: IdToken): Result<Account> {
        // メアド認証の場合、サインアップとサインインが明確に分離しているので特に考慮せずそのまま流して良い
        val account = FirebaseAuth.getInstance().verifyIdToken(credential.value, true)

        return Result.success(
            Account(
                uid = account.uid,
            ),
        )
    }

    override suspend fun signIn(credential: IdToken): Result<Account> {
        // メアド認証の場合、サインアップとサインインが明確に分離しているので特に考慮せずそのまま流して良い
        val account = FirebaseAuth.getInstance().verifyIdToken(credential.value, true)

        return Result.success(
            Account(
                uid = account.uid,
            ),
        )
    }
}
