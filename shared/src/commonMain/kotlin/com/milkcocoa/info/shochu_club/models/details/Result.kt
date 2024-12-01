package com.milkcocoa.info.shochu_club.models.details

import kotlinx.serialization.Serializable

@Serializable(with = ResultSerializer::class)
sealed class Result<out V : ResponseDataEntity> {
    data class Success<V : ResponseDataEntity>(
        val value: V,
    ) : Result<V>()

    data class Failure(
        val errors: List<ErrorMessage>,
    ) : Result<Nothing>()
}
