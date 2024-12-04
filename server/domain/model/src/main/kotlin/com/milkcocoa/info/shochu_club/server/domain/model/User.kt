package com.milkcocoa.info.shochu_club.server.domain.model

import java.time.LocalDate
import java.util.*

data class User(
    val userId: UUID,
    val birthday: LocalDate,
    val nickname: String,
    val firebaseUid: String,
    val comment: String,
    val iconUrl: String,
)
