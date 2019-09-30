package proj.skb.config

import org.apache.ignite.Ignite
import org.apache.ignite.Ignition
import org.apache.ignite.configuration.DataStorageConfiguration
import org.apache.ignite.configuration.IgniteConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import proj.skb.common.CacheObject
import javax.cache.Cache

/**
 * Spring library for Ignite uses version 1.4.199 and glitches.
 * So that here is native version of Ignite is used that is version 2.7.6
 */
@Configuration
class IgniteConfig(private val properties: CacheProperties) {

    @Bean
    fun skbIgniteConfig() = IgniteConfiguration().apply {
        // Setting some custom name for the node.
        igniteInstanceName = properties.igniteCacheName

        // Enabling peer-class loading feature.
        isPeerClassLoadingEnabled = true
        dataStorageConfiguration = DataStorageConfiguration()

        // Defining and creating a new cache to be used by Ignite Spring Data
        // repository.
//        val ccfg = CacheConfiguration("PersonCache")
//
//        // Setting SQL schema for the cache.
//        ccfg.setIndexedTypes(Long::class.java, Person::class.java)
//
//        cfg.setCacheConfiguration(ccfg)

    }

    @Bean
    fun igniteInstance(config: IgniteConfiguration): Ignite = Ignition.start(config)

    @Bean
    fun cache(igniteInstance: Ignite): Cache<String, CacheObject> = igniteInstance.getOrCreateCache(properties.igniteCacheName)

}


