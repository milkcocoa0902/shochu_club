package com.milkcocoa.info.shochu_club.server.infra.database.repository

import com.milkcocoa.info.shochu_club.server.domain.model.AccountSummary
import com.milkcocoa.info.shochu_club.server.domain.model.ProvisionedUser
import com.milkcocoa.info.shochu_club.server.domain.model.type.DeleteReasonValue
import com.milkcocoa.info.shochu_club.server.domain.repository.AccountRepository
import com.milkcocoa.info.shochu_club.server.infra.database.entities.AnonymousUser
import com.milkcocoa.info.shochu_club.server.infra.database.entities.ProvisionalRegistration
import com.milkcocoa.info.shochu_club.server.infra.database.entities.ShochuClubUser
import com.milkcocoa.info.shochu_club.server.infra.database.entities.SystemUid
import com.milkcocoa.info.shochu_club.server.infra.database.tables.anonymous_user.anonymous_user
import com.milkcocoa.info.shochu_club.server.infra.database.tables.common.*
import com.milkcocoa.info.shochu_club.server.infra.database.tables.provisional_registrations.RegistrationType
import com.milkcocoa.info.shochu_club.server.infra.database.tables.provisional_registrations.provisional_registration
import com.milkcocoa.info.shochu_club.server.infra.database.tables.sohchu_club_user.*
import com.milkcocoa.info.shochu_club.server.infra.database.tables.system_uid.IsAnonymousUser
import com.milkcocoa.info.shochu_club.server.infra.database.tables.system_uid.IsDeleted
import com.milkcocoa.info.shochu_club.server.infra.database.tables.system_uid.UserName
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime
import java.util.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

class AccountRepositoryImpl(): AccountRepository {
    private fun getRandomUserName(
        length: Int = 8
    ): String =
        (1..length)
            .map { (('a'..'z') + ('A'..'Z') + ('0'..'9') + '_').random() }
            .joinToString("")

    @ExperimentalUuidApi
    override suspend fun issueSystemUid(anonymously: Boolean): Uuid {
        return SystemUid.new {
            this.isAnonymousUser = IsAnonymousUser(anonymously)
            this.username = UserName(getRandomUserName(length = 12))
            this.createdAt = LocalDateTime.now().toKotlinLocalDateTime()
            this.isDeleted = IsDeleted(false)
            this.deleteReason = null
            this.deletedAt = null
        }.id.value.toKotlinUuid()
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun createUserAnonymously(systemUid: Uuid): AccountSummary {
        val systemUser = SystemUid.get(systemUid.toJavaUuid())

        val anonymousUser = AnonymousUser.new {
            this.systemUid = systemUser
            this.comment = Comment("")
            this.createdAt = LocalDateTime.now().toKotlinLocalDateTime()
            this.updatedAt = LocalDateTime.now().toKotlinLocalDateTime()
            this.nickname = NickName("匿名ユーザ")
            this.deleteReason = null
            this.deletedAt = null
        }

        return AccountSummary(
            systemUid = systemUser.id.value.toKotlinUuid(),
            isAnonymous = 1,
            userName = systemUser.username.value,
            iconUrl = "",
            comment = anonymousUser.comment.value,
            nickName = anonymousUser.nickname.value,
        )

    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun provisionalSignUpFromAnonymousUser(
        systemUid: Uuid,
        email: String,
        passwordHash: String,
        authProvider: Int
    ) : ProvisionedUser{
        val systemUser = SystemUid.get(systemUid.toJavaUuid())

        val provisionalRegistration = ProvisionalRegistration.new {
            this.systemUid = systemUser
            this.email = Email(email)
            this.passwordHash = PasswordHash(passwordHash)
            this.createdAt = LocalDateTime.now().toKotlinLocalDateTime()
            this.registrationType = RegistrationType(1)
        }

        return ProvisionedUser(
            provisionedUserId = provisionalRegistration.id.value.toKotlinUuid(),
            systemUid = provisionalRegistration.systemUid.id.value.toKotlinUuid(),
            email = provisionalRegistration.email.value,
            passwordHash = provisionalRegistration.passwordHash.value,
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun createAuthenticatedUser(
        systemUid: Uuid,
        email: String,
        passwordHash: String,
        authProvider: Int
    ): AccountSummary {
        val systemUser = SystemUid.get(systemUid.toJavaUuid())
        val account = ShochuClubUser.new {
            this.systemUid = systemUser
            this.email = Email(email)
            this.isEmailVerified = IsEmailVerified(true)
            this.passwordHash = PasswordHash(passwordHash)
            this.passwordChangedAt = LocalDateTime.now().toKotlinLocalDateTime()
            this.failedLoginAttempts = FailedLoginAttempt(0)
            this.authProvider = AuthProvider(authProvider)
            this.isEnabled = IsEnabled(true)
            this.isDeleted = IsDeleted(false)
            this.isLocked = IsLocked(false)
            this.multiFactorAuthEnabled = MultiFactorAuthEnabled(false)
            this.multiFactorAuthSecret = null
            this.birthday = null
            this.nickName = NickName("")
            this.comment = Comment("")
            this.createdAt = LocalDateTime.now().toKotlinLocalDateTime()
            this.updatedAt = LocalDateTime.now().toKotlinLocalDateTime()
            this.lastLoginAt = LocalDateTime.now().toKotlinLocalDateTime()
        }
        return AccountSummary(
            systemUid = systemUser.id.value.toKotlinUuid(),
            isAnonymous = 1,
            userName = systemUser.username.value,
            iconUrl = "",
            comment = account.comment.value,
            nickName = account.nickName.value,
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateUsername(
        systemUid: Uuid,
        username: String
    ) {
        val systemUser = SystemUid.get(systemUid.toJavaUuid())
        systemUser.username = UserName(username)
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun findUserInformationByUid(
        systemUid: Uuid
    ): AccountSummary? {
        val systemUser = SystemUid.get(systemUid.toJavaUuid())
        if(systemUser.isAnonymousUser.value){
            val anonymousUser = AnonymousUser.find { anonymous_user.uid eq systemUser.id.value }.first()
            return AccountSummary(
                systemUid = systemUser.id.value.toKotlinUuid(),
                isAnonymous = 1,
                userName = systemUser.username.value,
                iconUrl = "",
                comment = anonymousUser.comment.value,
                nickName = anonymousUser.nickname.value,
            )
        }else{
            val authenticatedUser = ShochuClubUser.find { shochu_club_user.uid eq systemUser.id.value }.first()
            return AccountSummary(
                systemUid = systemUser.id.value.toKotlinUuid(),
                isAnonymous = 1,
                userName = systemUser.username.value,
                iconUrl = "",
                comment = authenticatedUser.comment.value,
                nickName = authenticatedUser.nickName.value,
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateUserInformation(
        systemUid: Uuid,
        nickname: String,
        comment: String
    ): AccountSummary {
        val systemUser = SystemUid.get(systemUid.toJavaUuid())

        val authenticatedUser = ShochuClubUser.find { shochu_club_user.uid eq systemUser.id.value }.first()
        authenticatedUser.nickName = NickName(nickname)
        authenticatedUser.comment = Comment(comment)


        return AccountSummary(
            systemUid = systemUser.id.value.toKotlinUuid(),
            isAnonymous = 1,
            userName = systemUser.username.value,
            iconUrl = "",
            comment = authenticatedUser.comment.value,
            nickName = authenticatedUser.nickName.value,
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun findProvisionedUserByEmail(
        email: String
    ): ProvisionedUser? {
        return ProvisionalRegistration.find { provisional_registration.email eq Email(email) }.firstOrNull()?.let {
            ProvisionedUser(
                provisionedUserId = it.id.value.toKotlinUuid(),
                systemUid = it.systemUid.id.value.toKotlinUuid(),
                email = it.email.value,
                passwordHash = it.passwordHash.value,
            )
        } ?: error("")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun promoteSystemUidIntoAuthenticated(
        systemUid: Uuid
    ) {
        SystemUid.get(systemUid.toJavaUuid()).isAnonymousUser = IsAnonymousUser(false)
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateUserInfoAnonymously(
        systemUid: Uuid,
        nickname: String,
        comment: String
    ): AccountSummary {
        val systemUser = SystemUid.get(systemUid.toJavaUuid())

        val anonymousUser = AnonymousUser.find { anonymous_user.uid eq systemUser.id.value }.first()
        anonymousUser.nickname = NickName(nickname)
        anonymousUser.comment = Comment(comment)


        return AccountSummary(
            systemUid = systemUser.id.value.toKotlinUuid(),
            isAnonymous = 1,
            userName = systemUser.username.value,
            iconUrl = "",
            comment = anonymousUser.comment.value,
            nickName = anonymousUser.nickname.value,
        )
    }

    override suspend fun deleteProvisionedUserIfExists(
        email: String
    ): Boolean {
        return kotlin.runCatching {
            ProvisionalRegistration.find { provisional_registration.email eq Email(email) }.firstOrNull()?.delete()

            return@runCatching true
        }.getOrElse { return@getOrElse false }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteAnonymousUserLogically(
        systemUid: Uuid,
        deleteReason: DeleteReasonValue
    ): Boolean {
        return kotlin.runCatching {
            val systemUser = SystemUid.get(systemUid.toJavaUuid())

            val anonymousUser = AnonymousUser.find { anonymous_user.uid eq systemUser.id.value }.firstOrNull()
            anonymousUser?.deletedAt = LocalDateTime.now().toKotlinLocalDateTime()
            anonymousUser?.deleteReason = DeleteReason(deleteReason)
            return@runCatching true

        }.getOrElse { return@getOrElse false }
    }

    override suspend fun checkUserExists(email: String): Boolean {
        return ShochuClubUser.find { shochu_club_user.email eq Email(email) }.empty().not()
    }
}