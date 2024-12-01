import com.milkcocoa.info.shochu_club.server.presentation.controller.AccountManagementRPCServiceImpl
import com.milkcocoa.info.shochu_club.services.AccountManagementRPCService
import io.ktor.resources.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import io.ktor.server.routing.get
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.protobuf.protobuf
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
fun Route.accountRoute() {
    rpc("/account") {
        rpcConfig {
            serialization {
                protobuf()
            }
        }

        registerService<AccountManagementRPCService> { ctx -> AccountManagementRPCServiceImpl(ctx) }
    }
}
