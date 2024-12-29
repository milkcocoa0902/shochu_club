package com.milkcocoa.info.shochu_club.server.infra.database.repository

import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.model.MediaResolutionVariant
import com.milkcocoa.info.shochu_club.server.domain.model.StoredMediaObject
import com.milkcocoa.info.shochu_club.server.domain.model.type.AuthProviderType
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
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import java.time.ZoneOffset
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
            this.createdAt = Clock.System.now()
                .toJavaInstant()
                .atOffset(ZoneOffset.UTC)
            this.updatedAt = Clock.System.now()
                .toJavaInstant()
                .atOffset(ZoneOffset.UTC)
            this.isDeleted = IsDeleted(false)
            this.deleteReason = null
            this.deletedAt = null
        }.id.value.toKotlinUuid()
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun createUserAnonymously(systemUid: Uuid): Account.AnonymousUser {
        val systemUser = SystemUid.get(systemUid.toJavaUuid())

        val anonymousUser = AnonymousUser.new {
            this.systemUid = systemUser
            this.comment = Comment("")
            this.createdAt = Clock.System.now()
                .toJavaInstant()
                .atOffset(ZoneOffset.UTC)
            this.updatedAt = Clock.System.now()
                .toJavaInstant()
                .atOffset(ZoneOffset.UTC)
            this.nickname = NickName("匿名ユーザ")
            this.deleteReason = null
            this.deletedAt = null
        }

        return Account.AnonymousUser(
            systemUid = systemUser.id.value.toKotlinUuid(),
            userName = systemUser.username.value,
            iconUrl = StoredMediaObject.Image.NoData,
            comment = anonymousUser.comment.value,
            nickName = anonymousUser.nickname.value,
            registeredAt = anonymousUser.createdAt.toInstant().toKotlinInstant()
        )

    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun provisionalSignUpFromAnonymousUser(
        systemUid: Uuid,
        email: String,
        passwordHash: String,
        authProvider: AuthProviderType
    ) : Account.ProvisionedUser {
        val systemUser = SystemUid.get(systemUid.toJavaUuid())

        val provisionalRegistration = ProvisionalRegistration.new {
            this.systemUid = systemUser
            this.email = Email(email)
            this.passwordHash = PasswordHash(passwordHash)
            this.createdAt = Clock.System.now()
                .toJavaInstant()
                .atOffset(ZoneOffset.UTC)
            this.registrationType = RegistrationType(1)
        }

        return Account.ProvisionedUser(
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
        authProvider: AuthProviderType
    ): Account.AuthenticatedUser {
        val systemUser = SystemUid.get(systemUid.toJavaUuid())
        val account = ShochuClubUser.new {
            this.systemUid = systemUser
            this.email = Email(email)
            this.isEmailVerified = IsEmailVerified(true)
            this.passwordHash = PasswordHash(passwordHash)
            this.passwordChangedAt = Clock.System.now()
                .toJavaInstant()
                .atOffset(ZoneOffset.UTC)
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
            this.createdAt = Clock.System.now()
                .toJavaInstant()
                .atOffset(ZoneOffset.UTC)
            this.updatedAt = Clock.System.now()
                .toJavaInstant()
                .atOffset(ZoneOffset.UTC)
            this.lastLoginAt = Clock.System.now()
                .toJavaInstant()
                .atOffset(ZoneOffset.UTC)
            this.profileIconUrl = ProfileIconUrl(value = "")
        }
        return Account.AuthenticatedUser(
            systemUid = systemUser.id.value.toKotlinUuid(),
            userName = systemUser.username.value,
            iconUrl = StoredMediaObject.Image.UnResolved(
                id = Uuid.random(),
                key = account.profileIconUrl.value,
                resolution = MediaResolutionVariant.Original
            ),
            comment = account.comment.value,
            nickName = account.nickName.value,
            registeredAt = account.createdAt.toInstant().toKotlinInstant(),
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateUsername(
        systemUid: Uuid,
        username: String
    ) {
        val systemUser = SystemUid.get(systemUid.toJavaUuid())
        systemUser.username = UserName(username)

        systemUser.updatedAt = Clock.System.now()
            .toJavaInstant()
            .atOffset(ZoneOffset.UTC)
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun findUserInformationByUid(
        systemUid: Uuid
    ): Account? {
        val systemUser = SystemUid.get(systemUid.toJavaUuid())
        if(systemUser.isAnonymousUser.value){
            val anonymousUser = AnonymousUser.find { anonymous_user.uid eq systemUser.id.value }.first()
            return Account.AnonymousUser(
                systemUid = systemUser.id.value.toKotlinUuid(),
                userName = systemUser.username.value,
                iconUrl = StoredMediaObject.Image.NoData,
                comment = anonymousUser.comment.value,
                nickName = anonymousUser.nickname.value,
                registeredAt = anonymousUser.createdAt.toInstant().toKotlinInstant(),
            )
        }else{
            val authenticatedUser = ShochuClubUser.find { shochu_club_user.uid eq systemUser.id.value }.first()
            return Account.AuthenticatedUser(
                systemUid = systemUser.id.value.toKotlinUuid(),
                userName = systemUser.username.value,
                iconUrl = StoredMediaObject.Image.UnResolved(
                    id = Uuid.random(),
                    key = authenticatedUser.profileIconUrl.value,
                    resolution = MediaResolutionVariant.Original
                ),
                comment = authenticatedUser.comment.value,
                nickName = authenticatedUser.nickName.value,
                registeredAt = authenticatedUser.createdAt.toInstant().toKotlinInstant(),
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateUserInformation(
        systemUid: Uuid,
        nickname: String,
        comment: String
    ): Account {
        val systemUser = SystemUid.get(systemUid.toJavaUuid())

        systemUser.updatedAt = Clock.System.now()
            .toJavaInstant()
            .atOffset(ZoneOffset.UTC)

        if(systemUser.isAnonymousUser.value){
            val anonymousUser = AnonymousUser.find { anonymous_user.uid eq systemUser.id.value }.first()
            anonymousUser.comment = Comment(comment)
            anonymousUser.nickname = NickName(nickname)

            return Account.AnonymousUser(
                systemUid = systemUser.id.value.toKotlinUuid(),
                userName = systemUser.username.value,
                iconUrl = StoredMediaObject.Image.NoData,
                comment = anonymousUser.comment.value,
                nickName = anonymousUser.nickname.value,
                registeredAt = anonymousUser.createdAt.toInstant().toKotlinInstant(),
            )
        }else{
            val authenticatedUser = ShochuClubUser.find { shochu_club_user.uid eq systemUser.id.value }.first()
            authenticatedUser.comment = Comment(comment)
            authenticatedUser.nickName = NickName(nickname)

            return Account.AuthenticatedUser(
                systemUid = systemUser.id.value.toKotlinUuid(),
                userName = systemUser.username.value,
                iconUrl = StoredMediaObject.Image.UnResolved(
                    id = Uuid.random(),
                    key = authenticatedUser.profileIconUrl.value,
                    resolution = MediaResolutionVariant.Original
                ),
                comment = authenticatedUser.comment.value,
                nickName = authenticatedUser.nickName.value,
                registeredAt = authenticatedUser.createdAt.toInstant().toKotlinInstant(),
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun findProvisionedUserByEmail(
        email: String
    ): Account.ProvisionedUser? {
        return ProvisionalRegistration.find { provisional_registration.email eq Email(email) }.firstOrNull()?.let {
            Account.ProvisionedUser(
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
        SystemUid.get(systemUid.toJavaUuid()).run {
            isAnonymousUser = IsAnonymousUser(false)

            updatedAt = Clock.System.now()
                .toJavaInstant()
                .atOffset(ZoneOffset.UTC)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateUserInfoAnonymously(
        systemUid: Uuid,
        nickname: String,
        comment: String
    ): Account.AnonymousUser {
        val systemUser = SystemUid.get(systemUid.toJavaUuid())

        val anonymousUser = AnonymousUser.find { anonymous_user.uid eq systemUser.id.value }.first()
        anonymousUser.nickname = NickName(nickname)
        anonymousUser.comment = Comment(comment)


        return Account.AnonymousUser(
            systemUid = systemUser.id.value.toKotlinUuid(),
            userName = systemUser.username.value,
            iconUrl = StoredMediaObject.Image.NoData,
            comment = anonymousUser.comment.value,
            nickName = anonymousUser.nickname.value,
            registeredAt = anonymousUser.createdAt.toInstant().toKotlinInstant(),
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
            anonymousUser?.deletedAt = Clock.System.now()
                .toJavaInstant()
                .atOffset(ZoneOffset.UTC)
            anonymousUser?.deleteReason = DeleteReason(deleteReason)
            return@runCatching true

        }.getOrElse { return@getOrElse false }
    }

    override suspend fun checkUserExists(email: String): Boolean {
        return ShochuClubUser.find { shochu_club_user.email eq Email(email) }.empty().not()
    }
}