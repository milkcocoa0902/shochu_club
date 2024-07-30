package com.milkcocoa.info.shochu_club.server.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.model.IdToken
import com.milkcocoa.info.shochu_club.server.domain.repository.AccountRepository
import com.milkcocoa.info.shochu_club.server.domain.usecase.AccountSignInUseCase

class AccountSignInUseCaseImpl : AccountSignInUseCase {
    override suspend fun signIn(
        credential: IdToken,
        repository: AccountRepository,
    ): Result<Account> = repository.signIn(credential)
}
