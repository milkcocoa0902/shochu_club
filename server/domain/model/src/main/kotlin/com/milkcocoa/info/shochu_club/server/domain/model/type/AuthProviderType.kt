package com.milkcocoa.info.shochu_club.server.domain.model.type


enum class AuthProviderType(val value: Int){
    EmailAndPassword(1);

    companion object{
        fun valueOf(value: Int): AuthProviderType = entries.find { it.value == value } ?: error("Invalid value $value")
    }
}
