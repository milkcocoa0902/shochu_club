package com.milkcocoa.info.shochu_club.server.infra.database.tables.sohchu_club_user

import com.milkcocoa.info.evessa_fan_app.server.infra.database.column.booleanValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.column.integerValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.column.stringValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.tables.common.*
import com.milkcocoa.info.shochu_club.server.infra.database.tables.system_uid.IsDeleted
import com.milkcocoa.info.shochu_club.server.infra.database.tables.system_uid.system_uid
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone


@SuppressWarnings("ktlint:standard:class-naming", "standard:class-naming")
object shochu_club_user : UUIDTable("shochu_club_user", "id") {

    val uid = reference(
        "uid",
        system_uid,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val email = stringValueObject<Email>("email")
    val isEmailVerified = booleanValueObject<IsEmailVerified>("is_email_verified")
    val passwordHash = stringValueObject<PasswordHash>("password_hash")
    val passwordChangedAt = timestampWithTimeZone("password_changed_at")
    val failedLoginAttempts = integerValueObject<FailedLoginAttempt>("failed_login_attempts")
    val authProvider = integerValueObject<AuthProvider>("auth_provider")
    val isEnabled = booleanValueObject<IsEnabled>("is_enabled")
    val isDeleted = booleanValueObject<IsDeleted>("is_deleted")
    val isLocked = booleanValueObject<IsLocked>("is_locked")
    val multiFactorAuthEnabled = booleanValueObject<MultiFactorAuthEnabled>("multi_factor_auth_enabled")
    val multiFactorAuthSecret = stringValueObject<MultiFactorAuthSecret>("multi_factor_auth_secret").nullable()
    val birthday = date("birthday").nullable()
    val nickname = stringValueObject<NickName>("nickname")
    val comment = stringValueObject<Comment>("comment")
    val profileIconUrl = stringValueObject<ProfileIconUrl>("profile_icon_url")
    val createdAt = timestampWithTimeZone("created_at")
    val updatedAt = timestampWithTimeZone("updated_at")
    val lastLoginAt = timestampWithTimeZone("last_login_at").nullable()
    val accountDisabledAt = timestampWithTimeZone("account_disabled_at").nullable()
    val accountDeletedAt = timestampWithTimeZone("account_deleted_at").nullable()
}
