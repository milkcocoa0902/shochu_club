package com.milkcocoa.info.shochu_club.server.infra.aws

import aws.sdk.kotlin.services.cloudfront.model.S3OriginConfig
import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.sdk.kotlin.services.s3.presigners.presignGetObject
import aws.smithy.kotlin.runtime.net.url.Url
import com.milkcocoa.info.shochu_club.server.domain.repository.DistributionBackend
import java.net.URL
import java.time.Duration
import kotlin.time.toKotlinDuration

class S3DistributionBackend(
    private val bucket: String,
    private val region: String,
    private val endpointUrl: URL? = null,
): DistributionBackend {
    override suspend fun generateSignedUrl(key: String, expires: Long): String {
        return S3Client.fromEnvironment{
            this.forcePathStyle = true
            this.region = this@S3DistributionBackend.region
            this.endpointUrl = this@S3DistributionBackend.endpointUrl?.let { Url.parse(it.toString()) }
        }.use { client ->
            return@use client.presignGetObject(
                GetObjectRequest.invoke {
                    this.key = key
                    this.bucket = this@S3DistributionBackend.bucket
                },
                duration = Duration.ofSeconds(expires).toKotlinDuration()
            ).url.toString()
        }
    }
}