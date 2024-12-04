package com.milkcocoa.info.shochu_club.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual object DispatcherProvider {
    actual val io: CoroutineDispatcher
        get() = Dispatchers.IO
    actual val main: CoroutineDispatcher
        get() = Dispatchers.Main
    actual val default: CoroutineDispatcher
        get() = Dispatchers.Default
}