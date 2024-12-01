package com.milkcocoa.info.shochu_club.server.domain.model.exception

final class UserDoesNotExists(
    override val message: String,
) : Throwable(message)
