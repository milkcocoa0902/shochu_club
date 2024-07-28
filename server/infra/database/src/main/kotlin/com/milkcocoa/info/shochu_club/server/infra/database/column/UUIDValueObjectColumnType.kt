package com.milkcocoa.info.shochu_club.server.infra.database.column

import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import java.nio.ByteBuffer
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class UUIDValueColumnType<T : ValueObject<UUID>>(
    private val type: KClass<T>,
) : ColumnType<T>() {
    override fun sqlType(): String =
        org.jetbrains.exposed.sql.vendors.currentDialect.dataTypeProvider
            .uuidType()

    @OptIn(ExperimentalStdlibApi::class)
    override fun valueFromDB(value: Any): T =
        when (value) {
            is ValueObject<*> -> value as T
            is ByteArray -> requireNotNull(type.primaryConstructor).call(ByteBuffer.wrap(value).let { UUID(it.getLong(), it.getLong()) })
            is Int ->
                requireNotNull(type.primaryConstructor).call(
                    ByteBuffer.wrap(value.toHexString().encodeToByteArray()).let {
                        UUID(it.getLong(), it.getLong())
                    },
                )
            is String -> requireNotNull(type.primaryConstructor).call(UUID.fromString(value))
            else -> error("Unexpected value: $value of ${type.qualifiedName}")
        }

    override fun notNullValueToDB(value: T): Any =
        when (value) {
            is ValueObject<UUID> -> {
                val buffer = ByteBuffer.allocate(16)
                buffer.putLong(value.value.mostSignificantBits)
                buffer.putLong(value.value.leastSignificantBits)

                buffer.array()
            }
            else -> error("Unexpected value of type: $value of ${value::class.qualifiedName}")
        }

    override fun nonNullValueToString(value: T): String = value.value.toString()

    companion object {
        inline operator fun <reified T : ValueObject<UUID>> invoke() = UUIDValueColumnType(T::class)
    }
}

inline fun <reified T : ValueObject<UUID>> Table.uuidValueObject(name: String) = registerColumn<T>(name, UUIDValueColumnType<T>())
