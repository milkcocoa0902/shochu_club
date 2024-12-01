package com.milkcocoa.info.shochu_club.server.domain.model.type


enum class DeleteReasonValue(val value: Int){
    PromoteToAuthenticated(1);

    companion object{
        fun valueOf(value: Int): DeleteReasonValue = entries.find { it.value == value } ?: error("Invalid value $value")
    }
}
