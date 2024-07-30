package com.milkcocoa.info.shochu_club.server.service

import com.milkcocoa.info.shochu_club.server.domain.model.IdToken
import com.milkcocoa.info.shochu_club.server.domain.model.User
import com.milkcocoa.info.shochu_club.server.domain.repository.AccountRepository
import com.milkcocoa.info.shochu_club.server.domain.service.AccountSignInService
import com.milkcocoa.info.shochu_club.server.domain.usecase.AccountSignInUseCase
import com.milkcocoa.info.shochu_club.server.domain.usecase.GetUserUseCase

class AccountSignInWithEmailService(
    private val accountSignInUseCase: AccountSignInUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val accountRepository: AccountRepository,
) : AccountSignInService {
    override suspend fun signIn(credential: IdToken): User {
        val account = accountSignInUseCase.signIn(credential, accountRepository)
        if (account.isFailure) {
            error("")
        }

        return getUserUseCase.findByUid(account.getOrThrow())
    }
}
