package com.milkcocoa.info.shochu_club.server.infra.aws

import com.milkcocoa.info.shochu_club.server.domain.repository.DistributionBackend
import java.nio.file.Path

class CloudFrontDistributionBackend(
    private val baseUrl: String,
    private val keyPairId: String,
    private val privateKey: Path
): DistributionBackend {
    override suspend fun generateSignedUrl(bucket: String, key: String, expires: Long): String {
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