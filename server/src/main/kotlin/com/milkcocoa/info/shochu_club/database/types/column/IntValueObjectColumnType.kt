package com.milkcocoa.info.shochu_club.database.types.column

import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class IntegerValueColumnType<T : ValueObject<Int>>(
    private val type: KClass<T>,
) : ColumnType<T>() {
    override fun sqlType(): String =
        org.jetbrains.exposed.sql.vendors.currentDialect.dataTypeProvider
            .integerType()

    override fun valueFromDB(value: Any): T =
        when (value) {
            is ValueObject<*> -> value as T
            is Int -> requireNotNull(type.primaryConstructor).call(value)
            is Number -> requireNotNull(type.primaryConstructor).call(value.toInt())
            is String -> requireNotNull(type.primaryConstructor).call(value.toInt())
            else -> error("Unexpected value: $value of ${type.qualifiedName}")
        }

    override fun notNullValueToDB(value: T): Any =
        when (value) {
            is ValueObject<Int> -> value.value
            else -> error("Unexpected value of type: $value of ${value::class.qualifiedName}")
        }

    override fun nonNullValueToString(value: T): String = value.value.toString()

    companion object {
        inline operator fun <reified T : ValueObject<Int>> invoke() = IntegerValueColumnType(T::class)
    }
}

inline fun <reified T : ValueObject<Int>> Table.integerValueObject(name: String) = registerColumn<T>(name, IntegerValueColumnType<T>())
