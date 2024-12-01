package com.milkcocoa.info.shochu_club.client

import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import kotlinx.rpc.krpc.ktor.client.installRPC
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.protobuf.protobuf
import kotlinx.serialization.ExperimentalSerializationApi

expect fun ktorHttpClient(): HttpClient


@OptIn(ExperimentalSerializationApi::class)
suspend fun ktorRpcClient(urlString: String) = ktorHttpClient().config {
    install(WebSockets)
    installRPC()
}.rpc(urlString = urlString) {
    rpcConfig {
        serialization {
            protobuf()
        }
    }
}