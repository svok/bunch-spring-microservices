package proj.skb.logics

import org.apache.ignite.Ignition
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RawIgniteTest {

    @Test
    fun igniteStartsTest() {
        val ignite = Ignition.start()
        assertThat(ignite).isNotNull
        Ignition.stopAll(true)
    }
}
