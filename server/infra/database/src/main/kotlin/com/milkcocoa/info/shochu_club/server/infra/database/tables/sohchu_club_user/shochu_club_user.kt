package com.milkcocoa.info.shochu_club.server.infra.database.tables.sohchu_club_user

import com.milkcocoa.info.evessa_fan_app.server.infra.database.column.booleanValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.column.integerValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.column.stringValueObject
import com.milkcocoa.info.shochu_club.server.infra.database.tables.common.*
import com.milkcocoa.info.shochu_club.server.infra.database.tables.iamge_resource.image_resource
import com.milkcocoa.info.shochu_club.server.infra.database.tables.system_uid.IsDeleted
import com.milkcocoa.info.shochu_club.server.infra.database.tables.system_uid.system_uid
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.kotlin.datetime.datetime


@SuppressWarnings("ktlint:standard:class-naming", "standard:class-naming")
object shochu_club_user : UUIDTable("shochu_club_user", "id") {

    val uid = reference(
        "system_uid",
        system_uid,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val email = stringValueObject<Email>("email")
    val isEmailVerified = booleanValueObject<IsEmailVerified>("is_email_verified")
    val passwordHash = stringValueObject<PasswordHash>("password_hash")
    val passwordChangedAt = datetime("password_changed_at")
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
    val iconUrl = reference(
        name = "icon_url",
        foreign = image_resource,
        onDelete = ReferenceOption.SET_NULL,
        onUpdate = ReferenceOption.CASCADE
    ).nullable()
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
    val lastLoginAt = datetime("last_login_at").nullable()
    val accountDisabledAt = datetime("account_disabled_at").nullable()
    val accountDeletedAt = datetime("account_deleted_at").nullable()
}
