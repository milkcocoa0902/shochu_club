package com.milkcocoa.info.shochu_club.server.domain.repository

interface DistributionBackend {
    suspend fun generateSignedUrl(bucket: String, key: String, expires: Long): String
}