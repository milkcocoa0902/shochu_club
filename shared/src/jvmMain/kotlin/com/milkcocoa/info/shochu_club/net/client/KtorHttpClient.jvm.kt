package com.milkcocoa.info.shochu_club.net.client

import io.ktor.client.*
import io.ktor.client.engine.cio.*

actual fun ktorHttpClient() = HttpClient(CIO){

}