package com.milkcocoa.info.shochu_club.server.application.controller

import com.milkcocoa.info.shochu_club.services.AwesomeService
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.rpc.serialization.protobuf
import kotlinx.rpc.transport.ktor.server.rpc
import javax.print.PrintServiceLookup.registerService
import kotlin.coroutines.CoroutineContext

class AwesomeServiceImpl(
    override val coroutineContext: CoroutineContext,
) : AwesomeService {
    override suspend fun getNews(city: String): Flow<String> =
        flow {
            emit("Today is 23 degrees!")
            emit("Harry Potter is in $city!")
            emit("New dogs cafe has opened doors to all fluffy customers!")
        }
}

fun Route.awesomeRoute() {
    rpc("/awesome") {
        rpcConfig {
            serialization {
                protobuf()
            }
        }

        registerService<AwesomeService> { ctx -> AwesomeServiceImpl(ctx) }
    }
}
