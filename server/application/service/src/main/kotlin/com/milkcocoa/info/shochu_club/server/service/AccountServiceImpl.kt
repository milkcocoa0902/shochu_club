package com.milkcocoa.info.shochu_club.server.service

import com.milkcocoa.info.shochu_club.server.domain.model.Account
import com.milkcocoa.info.shochu_club.server.domain.model.type.AuthProviderType
import com.milkcocoa.info.shochu_club.server.domain.model.type.DeleteReasonValue
import com.milkcocoa.info.shochu_club.server.domain.repository.AccountRepository
import com.milkcocoa.info.shochu_club.server.domain.repository.CacheRepository
import com.milkcocoa.info.shochu_club.server.domain.service.AccountService
import com.milkcocoa.info.shochu_club.server.domain.repository.MailBackend
import de.mkammerer.argon2.Argon2Factory
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.Duration
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val cacheRepository: CacheRepository,
    private val mailBackend: MailBackend
): AccountService {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun signUpAnonymously(): Account.AnonymousUser {
        return newSuspendedTransaction {
            val systemUid = accountRepository.issueSystemUid(anonymously = true)
            return@newSuspendedTransaction accountRepository.createUserAnonymously(systemUid)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateAnonymousUserInformation(
        systemUid: Uuid,
        nickname: String,
        comment: String
    ): Account.AnonymousUser {
        return newSuspendedTransaction {
            return@newSuspendedTransaction accountRepository.updateUserInfoAnonymously(
                systemUid = systemUid,
                nickname = nickname,
                comment = comment
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateUsername(systemUid: Uuid, username: String): Account {
        return newSuspendedTransaction {
            accountRepository.updateUsername(
                systemUid = systemUid,
                username = username
            )

            return@newSuspendedTransaction accountRepository.findUserInformationByUid(systemUid = systemUid)!!
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun provisionalSignUpFromAnonymousUser(
        systemUid: Uuid,
        email: String,
        passwordRaw: String,
        authProvider: AuthProviderType
    ): Account.ProvisionedUser {
        val mem = 65_536
        val iteration = 15
        val parallelism = 3
        val passwordHash = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id)
            .hash(
                iteration,
                mem,
                parallelism,
                passwordRaw.toCharArray(),
            )

        return newSuspendedTransaction {
            if(accountRepository.checkUserExists(email)){
                // すでに性会員登録されているメアドの場合はそもそも仮登録不可
                error("")
            }

            // すでに同じメアドで仮登録されている場合、削除しておく
            accountRepository.deleteProvisionedUserIfExists(email = email)

            // 仮登録レコードに記述する
            val provisionedUser = accountRepository.provisionalSignUpFromAnonymousUser(
                systemUid = systemUid,
                authProvider = authProvider,
                email = email,
                passwordHash = passwordHash
            )

            // 検証コードを生成する
            val confirmationCode =
                IntRange(0, 7)
                    .map { ('0'.. '9').random() }
                    .joinToString("")

            // 生成した検証コードをキャッシュに書いておく
            cacheRepository.storeCache(
                "confirmation_code_${provisionedUser.provisionedUserId}".replace('-', '_'),
                confirmationCode.toByteArray(),
                Duration.ofMinutes(15).seconds
            )

            // 検証コードを送信する
            mailBackend.sendEmail(
                from = "",
                to = listOf(email),
                subject = "検証コードの送信",
                body = """
                    焼酎クラブへの登録ありがとうございます。
                    メールアドレスの確認のための検証コードを送信いたします。
                    
                    
                    $confirmationCode
                    
                    なお、このメールに心当たりのない場合には破棄していただくようお願いいたします。
                    
                    ----
                    焼酎クラブ　運営
                    
                """.trimIndent()
            )

            return@newSuspendedTransaction provisionedUser
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun promoteProvisionedUser(
        email: String,
        passwordRaw: String,
        confirmationCode: String
    ): Account.AuthenticatedUser {
        return newSuspendedTransaction {
            if(accountRepository.checkUserExists(email)){
                // すでに性会員登録されているメアドの場合はそもそも仮登録不可
                // 最終的にはユニーク制約でブロックされるが。
                error("")
            }

            // 仮登録されたユーザを取得する
            val provisionedUser = accountRepository.findProvisionedUserByEmail(email) ?: error("")

            // 検証コードをキャッシュから読み、送信されたものと一致するかどうかを確認する。
            cacheRepository.readCache("confirmation_code_${provisionedUser.provisionedUserId}".replace('-', '_'))
                ?.decodeToString()
                ?.takeIf { it == confirmationCode } ?: error("")

            // 検証コードをキャッシュから削除する
            // この変更はロールバックされないが仕方ない
            cacheRepository.deleteCache("confirmation_code_${provisionedUser.provisionedUserId}")


            // 再送信されたパスワードハッシュで一致確認する
            Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id).verify(
                provisionedUser.passwordHash,
                passwordRaw.toCharArray(),
            ).takeIf { it } ?: error("")

            // UIDに紐づく情報が匿名から切り替わる前に取得しておく
            // 切り替わってから取得すると認証済みユーザ情報になってしまうので、順番依存
            val anonymousUserInfo = provisionedUser.systemUid?.let {
                accountRepository.findUserInformationByUid(it)
            }

            // 仮登録テーブルからIDを読む。なければ成り上がりではないので生成する
            val systemUid = provisionedUser.systemUid?.apply {
                // 既存のUIDをanonymousフラグを落とす
                accountRepository.promoteSystemUidIntoAuthenticated(systemUid = this)
            } ?: accountRepository.issueSystemUid(anonymously = false)

            // 認証済みユーザを作成する
            accountRepository.createAuthenticatedUser(
                systemUid = systemUid,
                email = provisionedUser.email,
                passwordHash = provisionedUser.passwordHash,
                authProvider = AuthProviderType.EmailAndPassword
            )



            // 匿名ユーザもニックネームとコメントを持っているので、認証済みユーザにコピーする
            val account = accountRepository.updateUserInformation(
                systemUid = systemUid,
                nickname = (anonymousUserInfo as? Account.AnonymousUser)?.nickName ?: "",
                comment = (anonymousUserInfo as? Account.AnonymousUser)?.comment ?: "",
            ) as Account.AuthenticatedUser

            // 仮登録アカウントを削除する
            accountRepository.deleteProvisionedUserIfExists(email = provisionedUser.email)

            // 匿名時代のアカウント情報を論理削除する
            // TODO("物理削除でも良くない......？")
            accountRepository.deleteAnonymousUserLogically(
                systemUid = systemUid,
                deleteReason = DeleteReasonValue.PromoteToAuthenticated
            )


            return@newSuspendedTransaction account
        }
    }
}