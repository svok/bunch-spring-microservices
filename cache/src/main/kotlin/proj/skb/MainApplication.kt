package proj.skb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import proj.skb.config.CacheProperties

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(CacheProperties::class)
class MainApplication

fun main(args: Array<String>) {
    runApplication<MainApplication>(args = *args)
}
