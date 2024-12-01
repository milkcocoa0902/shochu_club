package com.milkcocoa.info.shochu_club.models.details

import kotlinx.serialization.Serializable

@Serializable
sealed class ErrorMessage(
    val message: String,
) {
    @Serializable
    data object UserDoesNotExists : ErrorMessage("User does not exist")

    @Serializable
    data object MissingDataEntity : ErrorMessage("Missing data for successful result")

    @Serializable
    data object UnhandledError : ErrorMessage("Unhandled error")
}
