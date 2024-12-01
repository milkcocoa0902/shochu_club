package com.milkcocoa.info.shochu_club.server.domain.repository

interface CacheRepository {
    fun storeCache(key: String, value: ByteArray): Unit
    fun storeCache(key: String, value: ByteArray, expireSeconds: Long): Unit
    fun readCache(key: String): ByteArray?
    fun deleteCache(key: String): Unit
}