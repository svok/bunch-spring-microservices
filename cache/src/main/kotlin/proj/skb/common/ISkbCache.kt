package proj.skb.common

import java.time.Duration

interface ISkbCache {
    fun setValue(cacheObject: CacheObject): ICacheContext
    fun setValue(data: Any, queueName: String = "", delay: Duration = Duration.ZERO): ICacheContext = setValue(
        CacheObject(
            data = data,
            queueName = queueName,
            delay = delay
        )
    )

    fun contains(context: ICacheContext): ICacheContext
    fun getValue(context: ICacheContext): ICacheContext

}
