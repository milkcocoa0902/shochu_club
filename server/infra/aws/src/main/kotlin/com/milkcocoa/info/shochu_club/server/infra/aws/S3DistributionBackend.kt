package com.milkcocoa.info.shochu_club.server.infra.aws

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.sdk.kotlin.services.s3.presigners.presignGetObject
import com.milkcocoa.info.shochu_club.server.domain.repository.DistributionBackend
import java.time.Duration
import kotlin.time.toKotlinDuration

class S3DistributionBackend: DistributionBackend {
    override suspend fun generateSignedUrl(bucket: String, key: String, expires: Long): String {
        return S3Client.fromEnvironment().use { client ->
            return@use client.presignGetObject(
                GetObjectRequest.invoke {
                    this.key = key
                    this.bucket = bucket
                },
                duration = Duration.ofSeconds(expires).toKotlinDuration()
            ).url.toString()
        }
    }
}