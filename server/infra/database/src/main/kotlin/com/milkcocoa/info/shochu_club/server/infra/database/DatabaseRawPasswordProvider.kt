package com.milkcocoa.info.shochu_club.server.infra.database

import com.milkcocoa.info.shochu_club.server.domain.repository.DatabasePasswordProvider

class DatabaseRawPasswordProvider(
    private val password: String,
): DatabasePasswordProvider {
    override suspend fun get(): String = password
}