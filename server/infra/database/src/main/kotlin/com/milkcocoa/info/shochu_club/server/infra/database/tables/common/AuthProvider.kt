package com.milkcocoa.info.shochu_club.server.infra.database.tables.common

import com.milkcocoa.info.shochu_club.server.domain.model.type.AuthProviderType
import com.milkcocoa.info.shochu_club.server.infra.database.column.ValueObject

class AuthProvider(value: AuthProviderType): ValueObject<Int>(value.value){
    constructor(value: Int): this(AuthProviderType.valueOf(value))
}