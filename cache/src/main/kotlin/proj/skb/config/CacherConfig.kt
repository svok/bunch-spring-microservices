package proj.skb.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import proj.skb.common.CacheObject
import proj.skb.common.ISkbCache
import proj.skb.logics.cacher.SkbCache
import java.time.Duration
import javax.cache.Cache

@Configuration
class CacherConfig(private val properties: CacheProperties) {

    @Bean
    fun cacher(cache: Cache<String, CacheObject>): ISkbCache = SkbCache(
        defatulDelay = Duration.parse(properties.defatulDelay),
        maxDelay = Duration.parse(properties.maxDelay),
        minDelay = Duration.parse(properties.minDelay),
        allowedQueues = properties.allowedQueues,
        cache = cache
    )

}
