import com.milkcocoa.info.shochu_club.net.rpc.AccountManagementRPCService
import com.milkcocoa.info.shochu_club.server.presentation.controller.AccountManagementRPCServiceImpl
import io.ktor.server.routing.*
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
