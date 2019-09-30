package proj.skb.config

import org.springframework.boot.context.properties.ConfigurationProperties
import proj.skb.ProjSkbConstants

@ConfigurationProperties("skb-cache")
data class CacheProperties(

    /**
     * A delay before object in cache is either deleted or sent to queue
     */
    val defatulDelay: String = ProjSkbConstants.DEFAULT_DELAY,

    /**
     * A maximum delay allowed in the query
     */
    val maxDelay: String = ProjSkbConstants.DEFAULT_MAX_DELAY,

    /**
     * A minimum delay allowed in the query
     */
    val minDelay: String = ProjSkbConstants.DEFAULT_MIN_DELAY,

    /**
     * A list of queues where a user and redirect the objects after delay
     */
    val allowedQueues: List<String> = ProjSkbConstants.DEFAULT_ALLOWED_QUEUES,

    /**
     * Cache name for Ignite
     */
    val igniteCacheName: String = ProjSkbConstants.DEFAULT_IGNITE_CACHE_NAME,

    /**
     * A configuration XML file for Ignite. Default is empty
     */
    val igniteConfigFile: String = ""
)
