package com.milkcocoa.info.shochu_club.server.infra.aws

import com.milkcocoa.info.shochu_club.server.domain.repository.DistributionBackend
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import java.time.Duration

class S3DistributionBackend: DistributionBackend {
    override fun generateSignedUrl(bucket: String, key: String, expires: Long): String {
        val presigner = S3Presigner.create()
        val getObjectRequest = GetObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .build()

        val presignRequest = GetObjectPresignRequest.builder()
            .signatureDuration(Duration.ofSeconds(expires))
            .getObjectRequest(getObjectRequest)
            .build()

        return presigner.presignGetObject(presignRequest).url().toString()
    }
}