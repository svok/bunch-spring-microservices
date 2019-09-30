package proj.skb.logics.cacher

import proj.skb.ProjSkbConstants
import proj.skb.common.CacheObject
import java.time.Duration

/**
 * Этот класс реализует логику работы с кэшем. Он сделан независимым от фреймворков и будет правильно рабтать не только
 * со Spring, но и с любым другим интерфейсом.
 */
class SimpleCache(
    defatulDelay: Duration = Duration.parse(ProjSkbConstants.DEFAULT_DELAY),
    maxDelay: Duration = Duration.parse(ProjSkbConstants.DEFAULT_MAX_DELAY),
    minDelay: Duration = Duration.parse(ProjSkbConstants.DEFAULT_MIN_DELAY),
    allowedQueues: Collection<String> = ProjSkbConstants.DEFAULT_ALLOWED_QUEUES
) : BaseCache(
    defatulDelay = defatulDelay,
    maxDelay = maxDelay,
    minDelay = minDelay,
    allowedQueues = allowedQueues
) {
    private val innerCache: MutableMap<String, CacheObject> = mutableMapOf()

    override fun saveInCache(key: String, value: CacheObject) {
        innerCache[key] = value
    }

    override fun getFromCache(key: String): CacheObject? = innerCache[key]

    override fun checkInCache(key: String): Boolean = innerCache.containsKey(key)

}
