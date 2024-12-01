package com.milkcocoa.info.shochu_club.server.infra.database.tables.sohchu_club_user

import com.milkcocoa.info.shochu_club.server.infra.database.column.ValueObject

class MultiFactorAuthSecret(value: String): ValueObject<String>(value)