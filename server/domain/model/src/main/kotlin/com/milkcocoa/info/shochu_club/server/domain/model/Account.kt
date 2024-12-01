package com.milkcocoa.info.shochu_club.server.domain.model

import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * アカウントの公開情報を持っているオブジェクト
 */
@OptIn(ExperimentalUuidApi::class)
data class AccountSummary(
    /**
     * システム上のID
     */
    val systemUid: Uuid,
    /**
     * ユーザが匿名なのか、登録済なのか
     */
    val isAnonymous: Int,
    /**
     * ユーザ名。Twitterでいう@以降
     */
    val userName: String,
    /**
     * スクリーンネーム
     */
    val nickName: String,
    /**
     * アイコン画像URL
     */
    val iconUrl: String,

    val comment: String,
)

@OptIn(ExperimentalUuidApi::class)
data class ProvisionedUser(
    val provisionedUserId: Uuid,
    val systemUid: Uuid?,
    val email: String,
    val passwordHash: String,
)