package proj.skb.common

import java.time.Duration
import java.time.Instant

data class CacheObject(
    val data: Any = EMPTY_DATA,
    val queueName: String = "",
    val delay: Duration = Duration.ZERO,
    val timeException: Instant = Instant.now() + delay
) {
    fun isEmpty() = data == EMPTY_DATA

    companion object {
        val EMPTY_DATA = Any()
        val NONE = CacheObject()
    }
}
