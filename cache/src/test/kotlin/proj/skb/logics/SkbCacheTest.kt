package proj.skb.logics

import org.apache.ignite.Ignite
import org.apache.ignite.Ignition
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import proj.skb.ProjSkbConstants
import proj.skb.common.CacheObject
import proj.skb.common.CacheStatus
import proj.skb.logics.cacher.SkbCache
import java.time.Duration
import javax.cache.Cache

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SkbCacheTest {

    private lateinit var ignite: Ignite
    private lateinit var cache: Cache<String, CacheObject>

    private val CACHE_NAME = "cacheName"

    @BeforeAll
    fun tearUp() {
        ignite = Ignition.start()!!
        cache = ignite.getOrCreateCache<String, CacheObject>(CACHE_NAME)!!
    }

    @AfterAll
    fun tearDown() {
        ignite.destroyCache(CACHE_NAME)
        Ignition.stopAll(true)
    }

    @Test
    fun setValueDefault() {
        val cacher = SkbCache(cache = cache)
        val res = cacher.setValue("value")

        assertThat(res.obj.data).isEqualTo("value")
        assertThat(res.obj.delay).isEqualTo(Duration.parse(ProjSkbConstants.DEFAULT_DELAY))
        assertThat(res.obj.queueName).isBlank()
        assertThat(res.status).isEqualTo(CacheStatus.OK)
        assertThat(res.isOk()).isTrue()
    }

    @Test
    fun setValueFull() {
        val cacher = SkbCache(cache = cache)
        val res = cacher.setValue("value", "", Duration.ofSeconds(2))

        assertThat(res.obj.data).isEqualTo("value")
        assertThat(res.obj.delay).isEqualTo(Duration.ofSeconds(2))
        assertThat(res.obj.queueName).isBlank()
        assertThat(res.status).isEqualTo(CacheStatus.OK)
        assertThat(res.isOk()).isTrue()
    }

    @Test
    fun setValueDelayValidation() {
        val cacher = SkbCache(cache = cache)
        val res = cacher.setValue("value", "", Duration.ofSeconds(500))

        assertThat(res.obj.data).isEqualTo("value")
        assertThat(res.obj.delay).isEqualTo(Duration.ofSeconds(500))
        assertThat(res.obj.queueName).isBlank()
        assertThat(res.status).isEqualTo(CacheStatus.FAIL)
        assertThat(res.isOk()).isFalse()
        assertThat(res.errorMessage).containsIgnoringCase("delay")
    }

    @Test
    fun setValueQueueValidation() {
        val cacher = SkbCache(cache = cache)
        val res = cacher.setValue("value", "someQueue")

        assertThat(res.obj.data).isEqualTo("value")
        assertThat(res.obj.delay).isEqualTo(Duration.parse(ProjSkbConstants.DEFAULT_DELAY))
        assertThat(res.obj.queueName).isEqualTo("someQueue")
        assertThat(res.status).isEqualTo(CacheStatus.FAIL)
        assertThat(res.isOk()).isFalse()
        assertThat(res.errorMessage).containsIgnoringCase("queue")
    }
}
