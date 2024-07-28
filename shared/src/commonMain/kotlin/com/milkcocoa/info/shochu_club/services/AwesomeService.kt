package com.milkcocoa.info.shochu_club.services

import kotlinx.coroutines.flow.Flow
import kotlinx.rpc.RPC

interface AwesomeService : RPC {
    suspend fun getNews(city: String): Flow<String>
}
