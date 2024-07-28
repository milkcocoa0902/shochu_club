package com.milkcocoa.info.shochu_club.database.types.column

open class ValueObject<V>(
    val value: V,
) {
    override fun toString(): String = value.toString()

    override fun equals(other: Any?): Boolean = (other as? ValueObject<*>)?.value == value

    override fun hashCode(): Int = value.hashCode()

    fun copy(): ValueObject<V> = ValueObject(value)

    fun copy(value: V): ValueObject<V> = ValueObject(value)
}
