package com.milkcocoa.info.shochu_club.server.infra.database.tables.common

import com.milkcocoa.info.shochu_club.server.infra.database.column.ValueObject

class PasswordHash(value: String): ValueObject<String>(value)