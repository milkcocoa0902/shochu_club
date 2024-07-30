package com.milkcocoa.info.shochu_club.models

import kotlinx.serialization.Serializable

@Serializable
sealed class AccountIdentifier

@Serializable
data class EmailAccountIdentifier(
    val email: String,
) : AccountIdentifier()

@Serializable
data class HandleNameAccountIdentifier(
    val handleName: String,
) : AccountIdentifier()

@Serializable
data class IdTokenAccountIdentifier(
    val idToken: String,
) : AccountIdentifier()
