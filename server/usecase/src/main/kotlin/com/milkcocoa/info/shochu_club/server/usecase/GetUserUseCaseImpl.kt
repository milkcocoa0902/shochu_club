package com.milkcocoa.info.shochu_club.server.usecase

import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.model.User
import com.milkcocoa.info.shochu_club.server.domain.repository.UserRepository
import com.milkcocoa.info.shochu_club.server.domain.usecase.GetUserUseCase

class GetUserUseCaseImpl(
    private val userRepository: UserRepository,
) : GetUserUseCase {
    override suspend fun findByUid(account: Account): User = userRepository.findUser(account)
}
