package com.milkcocoa.info.shochu_club.server.infra.aws

import aws.sdk.kotlin.services.secretsmanager.SecretsManagerClient
import aws.sdk.kotlin.services.secretsmanager.model.GetSecretValueRequest
import com.milkcocoa.info.shochu_club.server.domain.repository.DatabasePasswordProvider
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class RdsSecrets(
    val username: String,
    val password: String
)

class RdsSecretProvider(
    private val region: String,
    private val secretName: String,
): DatabasePasswordProvider {
    override suspend fun get(): String? {
        return SecretsManagerClient.fromEnvironment env@{
            this@env.region = this@RdsSecretProvider.region
        }.use { client ->
            client.getSecretValue(GetSecretValueRequest.invoke {
                this.secretId = secretName
            }).secretString?.let { secretString ->
                Json.decodeFromString<RdsSecrets>(secretString)
            }?.password
        }
    }
}
