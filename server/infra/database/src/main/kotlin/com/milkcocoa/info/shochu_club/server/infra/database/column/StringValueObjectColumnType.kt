package com.milkcocoa.info.shochu_club.server.infra.database.column

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class StringValueColumnType<T : ValueObject<String>>(
    private val type: KClass<T>,
) : ColumnType<T>() {
    override fun sqlType(): String =
        org.jetbrains.exposed.sql.vendors.currentDialect.dataTypeProvider
            .textType()

    @Suppress("UNCHECKED_CAST")
    override fun valueFromDB(value: Any): T =
        when (value) {
            is ValueObject<*> -> value as T
            is String -> requireNotNull(type.primaryConstructor).call(value)
            else -> error("Unexpected value: $value of ${type.qualifiedName}")
        }

    override fun notNullValueToDB(value: T): Any =
        when (value) {
            is ValueObject<String> -> value.value
            else -> error("Unexpected value of type: $value of ${value::class.qualifiedName}")
        }

    override fun nonNullValueToString(value: T): String =
        value.value
            .replace("'", "''")
            .replace("\\", "\\\\")
            .replace(";", "\\;")

    companion object {
        inline operator fun <reified T : ValueObject<String>> invoke() = StringValueColumnType(T::class)
    }
}

inline fun <reified T : ValueObject<String>> Table.stringValueObject(name: String) = registerColumn<T>(name, StringValueColumnType<T>())

fun <T : Comparable<T>> Table.customReference(
    name: String,
    foreignTable: Column<T>,
    onDelete: ReferenceOption = ReferenceOption.NO_ACTION,
    onUpdate: ReferenceOption = ReferenceOption.NO_ACTION,
): Column<T> {
    val column = registerColumn<T>(name, foreignTable.columnType)
    foreignKey(
        column to foreignTable,
        onDelete = onDelete,
        onUpdate = onUpdate,
    )
    return column
}
