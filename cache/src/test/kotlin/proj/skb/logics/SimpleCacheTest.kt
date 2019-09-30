package proj.skb.logics

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import proj.skb.ProjSkbConstants
import proj.skb.common.CacheStatus
import proj.skb.logics.cacher.SimpleCache
import java.time.Duration

internal class SimpleCacheTest {

    @Test
    fun setValueDefault() {
        val cacher = SimpleCache()
        val res = cacher.setValue("value")

        assertThat(res.obj.data).isEqualTo("value")
        assertThat(res.obj.delay).isEqualTo(Duration.parse(ProjSkbConstants.DEFAULT_DELAY))
        assertThat(res.obj.queueName).isBlank()
        assertThat(res.status).isEqualTo(CacheStatus.OK)
        assertThat(res.isOk()).isTrue()
    }

    @Test
    fun setValueFull() {
        val cacher = SimpleCache()
        val res = cacher.setValue("value", "", Duration.ofSeconds(2))

        assertThat(res.obj.data).isEqualTo("value")
        assertThat(res.obj.delay).isEqualTo(Duration.ofSeconds(2))
        assertThat(res.obj.queueName).isBlank()
        assertThat(res.status).isEqualTo(CacheStatus.OK)
        assertThat(res.isOk()).isTrue()
    }

    @Test
    fun setValueDelayValidation() {
        val cacher = SimpleCache()
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
        val cacher = SimpleCache()
        val res = cacher.setValue("value", "someQueue")

        assertThat(res.obj.data).isEqualTo("value")
        assertThat(res.obj.delay).isEqualTo(Duration.parse(ProjSkbConstants.DEFAULT_DELAY))
        assertThat(res.obj.queueName).isEqualTo("someQueue")
        assertThat(res.status).isEqualTo(CacheStatus.FAIL)
        assertThat(res.isOk()).isFalse()
        assertThat(res.errorMessage).containsIgnoringCase("queue")
    }
}
