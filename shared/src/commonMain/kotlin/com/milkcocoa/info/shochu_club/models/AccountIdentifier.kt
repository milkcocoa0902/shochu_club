package com.milkcocoa.info.shochu_club.models

import com.milkcocoa.info.shochu_club.models.details.ResponseDataEntity
import com.milkcocoa.info.shochu_club.serializers.UuidSerializer
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
sealed class AccountIdentifier : ResponseDataEntity

@Serializable
data class EmailAccountIdentifier(
    val uid: String,
) : AccountIdentifier()

@Serializable
data class GoogleAccountIdentifier(
    val uid: String,
) : AccountIdentifier()

@Serializable
data class HandleNameAccountIdentifier(
    val handleName: String,
) : AccountIdentifier()

@Serializable
data class IdTokenAccountIdentifier(
    val idToken: String,
) : AccountIdentifier()


@OptIn(ExperimentalUuidApi::class)
@Serializable
data class SystemUid (
    @Serializable(UuidSerializer::class)
    val uid: Uuid
): AccountIdentifier()