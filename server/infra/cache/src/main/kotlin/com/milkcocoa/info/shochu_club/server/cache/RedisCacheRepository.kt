package com.milkcocoa.info.shochu_club.server.cache

import com.milkcocoa.info.shochu_club.server.domain.repository.CacheRepository
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import java.util.concurrent.TimeUnit

class RedisCacheRepository(
    private val host: String,
    private val port: Int = 6379,
    private val password: String? = null,
    private val useSsl: Boolean = false,
): CacheRepository {
    private val jedis: Jedis = Jedis(host, port, useSsl).apply {
        connect()
    }
    override fun storeCache(key: String, value: ByteArray) {
        jedis.set(key.toByteArray(), value)
    }

    override fun readCache(key: String): ByteArray? {
        return jedis.get(key.toByteArray())
    }

    override fun storeCache(key: String, value: ByteArray, expireSeconds: Long) {
        jedis.setex(key.toByteArray(), expireSeconds, value)
    }

    override fun deleteCache(key: String) {
        jedis.del(key)
    }
}