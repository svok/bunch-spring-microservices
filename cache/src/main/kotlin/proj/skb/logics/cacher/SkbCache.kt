package proj.skb.logics.cacher

import proj.skb.ProjSkbConstants
import proj.skb.common.CacheObject
import java.time.Duration
import javax.cache.Cache

/**
 * Этот класс реализует логику работы с кэшем Ignite. Он сделан независимым от фреймворков и будет правильно работать
 * не только со Spring, но и с любым другим интерфейсом.
 */
class SkbCache(
    defatulDelay: Duration = Duration.parse(ProjSkbConstants.DEFAULT_DELAY),
    maxDelay: Duration = Duration.parse(ProjSkbConstants.DEFAULT_MAX_DELAY),
    minDelay: Duration = Duration.parse(ProjSkbConstants.DEFAULT_MIN_DELAY),
    allowedQueues: Collection<String> = ProjSkbConstants.DEFAULT_ALLOWED_QUEUES,
    private val cache: Cache<String, CacheObject>
) : BaseCache(
    defatulDelay = defatulDelay,
    maxDelay = maxDelay,
    minDelay = minDelay,
    allowedQueues = allowedQueues
) {
    override fun saveInCache(key: String, value: CacheObject) {
        cache.put(key, value)
    }

    override fun getFromCache(key: String): CacheObject? = cache.get(key)

    override fun checkInCache(key: String): Boolean = cache.containsKey(key)
}
