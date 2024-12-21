package com.milkcocoa.info.shochu_club.server.domain.repository

interface DatabasePasswordProvider {
    suspend fun get(): String?
}