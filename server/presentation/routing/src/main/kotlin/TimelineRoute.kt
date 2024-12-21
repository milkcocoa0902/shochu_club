import com.milkcocoa.info.shochu_club.net.rpc.AccountManagementRPCService
import com.milkcocoa.info.shochu_club.net.rpc.TimelineRPCService
import com.milkcocoa.info.shochu_club.server.presentation.controller.AccountManagementRPCServiceImpl
import com.milkcocoa.info.shochu_club.server.presentation.controller.TimelineRPCServiceImpl
import io.ktor.server.routing.*
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.protobuf.protobuf
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
fun Route.timelineRoute() {
    rpc("/timeline") {
        rpcConfig {
            serialization {
                protobuf()
            }
        }

        registerService<TimelineRPCService> { ctx -> TimelineRPCServiceImpl(ctx) }
    }
}
