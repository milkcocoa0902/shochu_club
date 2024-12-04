package com.milkcocoa.info.shochu_club.coroutine

import kotlinx.coroutines.CoroutineDispatcher

expect object DispatcherProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
}