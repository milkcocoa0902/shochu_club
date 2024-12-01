package com.milkcocoa.info.shochu_club.server.infra.database.tables.user_session

import com.milkcocoa.info.shochu_club.server.infra.database.column.ValueObject

class RefreshTokenHash(value: String): ValueObject<String>(value)