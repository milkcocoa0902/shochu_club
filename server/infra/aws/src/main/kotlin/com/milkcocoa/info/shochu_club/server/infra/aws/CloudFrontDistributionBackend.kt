package com.milkcocoa.info.shochu_club.server.infra.aws

import com.milkcocoa.info.shochu_club.server.domain.repository.DistributionBackend
import software.amazon.awssdk.services.cloudfront.CloudFrontUtilities
import java.nio.file.Path
import java.security.KeyStore.PrivateKeyEntry
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

class CloudFrontDistributionBackend(
    private val baseUrl: String,
    private val keyPairId: String,
    private val privateKey: Path
): DistributionBackend {
    override fun generateSignedUrl(bucket: String, key: String, expires: Long): String {
        val url = "${baseUrl.trimEnd('/')}/$bucket/$key"
        return url

//        val expiration = ZonedDateTime.now(ZoneOffset.UTC).plusSeconds(expires)
//        return CloudFrontUtilities.create().getSignedUrlWithCannedPolicy {
//            it.resourceUrl(url)
//            it.keyPairId(keyPairId)
//            it.privateKey(privateKey)
//            it.expirationDate(expiration.toInstant())
//        }.url()
    }
}