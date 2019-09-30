package proj.skb.models

import proj.skb.common.CacheStatus
import proj.skb.common.ICacheContext
import java.time.Duration


data class RestCacheResponse(
    val id: String = "",
    val data: Any? = null,
    val queue: String? = null,
    val delay: String? = null,
    val status: RestCacheStatus = RestCacheStatus.NONE,
    val errorMessage: String? = null,
    val stub: String? = null
) {
    companion object {
        fun of(from: ICacheContext) = RestCacheResponse(
            id = from.id,
            data = from.obj.takeIf { ! it.isEmpty() }?.data,
            queue = from.obj.queueName,
            delay = from.obj.delay.takeIf { it != Duration.ZERO }?.toString(),
            status = RestCacheStatus.of(from.status),
            errorMessage = from.errorMessage.takeIf { it.isNotBlank() },
            stub = from.stub.takeIf { it.isNotBlank() }
        )

        fun of(e: Throwable, data: Any?, queueName: String?, delay: String?) = RestCacheResponse(
            id = "",
            data = data,
            queue = queueName,
            delay = delay,
            status = RestCacheStatus.FAIL,
            errorMessage = e.message ?: "Error",
            stub = null
        )
    }
}
