package com.milkcocoa.info.shochu_club.models.details

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class ResultSerializer<T : ResponseDataEntity>(
    tSerializer: KSerializer<T>,
) : KSerializer<Result<T>> {
    @Serializable
    @SerialName("Result")
    data class ResultSurrogate<T : ResponseDataEntity>
        @OptIn(ExperimentalSerializationApi::class)
        constructor(
            val type: Type,
            @EncodeDefault(EncodeDefault.Mode.NEVER)
            val data: T? = null,
            @EncodeDefault(EncodeDefault.Mode.NEVER)
            val errors: List<ErrorMessage>? = null,
        ) {
            enum class Type { SUCCESS, FAILURE }
        }

    private val surrogateSerializer = ResultSurrogate.serializer(tSerializer)
    override val descriptor: SerialDescriptor = surrogateSerializer.descriptor

    override fun deserialize(decoder: Decoder): Result<T> {
        val surrogate = surrogateSerializer.deserialize(decoder)
        return when (surrogate.type) {
            ResultSurrogate.Type.SUCCESS -> {
                if (surrogate.data != null) {
                    Result.Success(surrogate.data)
                } else {
                    Result.Failure(errors = listOf(ErrorMessage.MissingDataEntity))
                }
            }
            ResultSurrogate.Type.FAILURE -> {
                Result.Failure(surrogate.errors ?: listOf())
            }
        }
    }

    override fun serialize(
        encoder: Encoder,
        value: Result<T>,
    ) {
        val surrogate =
            when (value) {
                is Result.Failure ->
                    ResultSurrogate(
                        type = ResultSurrogate.Type.FAILURE,
                        errors = value.errors,
                    )
                is Result.Success ->
                    ResultSurrogate(
                        type = ResultSurrogate.Type.SUCCESS,
                        data = value.value,
                    )
            }
        surrogateSerializer.serialize(encoder, surrogate)
    }
}
