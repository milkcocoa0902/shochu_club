package com.milkcocoa.info.shochu_club.services

import com.milkcocoa.info.shochu_club.models.AccountIdentifier
import com.milkcocoa.info.shochu_club.models.EmailAccountIdentifier
import com.milkcocoa.info.shochu_club.models.IdTokenAccountIdentifier
import kotlinx.rpc.RPC

interface AccountManagementService : RPC {
    suspend fun checkAccountExistence(identifier: AccountIdentifier): Boolean

    suspend fun signInWithEmail(idToken: IdTokenAccountIdentifier): EmailAccountIdentifier

    suspend fun signin(identifier: AccountIdentifier)

    suspend fun signup(identifier: AccountIdentifier)

    suspend fun logout(identifier: AccountIdentifier)

    suspend fun deleteAccount(
        identifier: AccountIdentifier,
        deleteAccountToken: String,
    )
}
