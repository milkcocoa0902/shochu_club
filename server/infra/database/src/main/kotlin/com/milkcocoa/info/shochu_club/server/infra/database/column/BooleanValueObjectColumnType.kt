package com.milkcocoa.info.evessa_fan_app.server.infra.database.column

import com.milkcocoa.info.shochu_club.server.infra.database.column.ValueObject
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class BooleanValueColumnType<T : ValueObject<Boolean>>(
    private val type: KClass<T>,
) : ColumnType<T>() {
    override fun sqlType(): String =
        org.jetbrains.exposed.sql.vendors.currentDialect.dataTypeProvider
            .integerType()

    @Suppress("UNCHECKED_CAST")
    override fun valueFromDB(value: Any): T =
        when (value) {
            is ValueObject<*> -> value as T
            is Boolean -> requireNotNull(type.primaryConstructor).call(value)
            is Int -> requireNotNull(type.primaryConstructor).call(value == 1)
            is Number -> requireNotNull(type.primaryConstructor).call(value.toInt() == 1)
            is String -> requireNotNull(type.primaryConstructor).call(value.uppercase().takeIf { it == "TRUE" } ?: false)
            else -> error("Unexpected value: $value of ${type.qualifiedName}")
        }

    override fun notNullValueToDB(value: T): Any =
        when (value) {
            is ValueObject<Boolean> -> value.value
            else -> error("Unexpected value of type: $value of ${value::class.qualifiedName}")
        }

    override fun nonNullValueToString(value: T): String = value.value.toString()

    companion object {
        inline operator fun <reified T : ValueObject<Boolean>> invoke() = BooleanValueColumnType(T::class)
    }
}

inline fun <reified T : ValueObject<Boolean>> Table.booleanValueObject(name: String) = registerColumn<T>(name, BooleanValueColumnType<T>())
