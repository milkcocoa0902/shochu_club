package com.milkcocoa.info.shochu_club.net.rpc

import com.milkcocoa.info.shochu_club.models.details.Result
import com.milkcocoa.info.shochu_club.models.feed.Timeline
import com.milkcocoa.info.shochu_club.models.user.ShochuClubUserId
import com.milkcocoa.info.shochu_club.net.client.ktorRpcClient
import kotlinx.rpc.RemoteService
import kotlinx.rpc.annotations.Rpc
import kotlinx.rpc.withService

@Rpc
interface TimelineRPCService: RemoteService {
    suspend fun userTimeline(
        userId: ShochuClubUserId
    ): Result<Timeline>

    suspend fun homeTimeline(
        uid: ShochuClubUserId,
        cursor: String?,
        count: Int?,
    ): Result<Timeline>
}


suspend fun timelineRpcClient(urlString: String) = ktorRpcClient(urlString).withService<TimelineRPCService>()

