package proj.skb.models

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import proj.skb.common.CacheStatus
import com.fasterxml.jackson.annotation.JsonValue



enum class RestCacheStatus(base: CacheStatus) {
    @JsonProperty("ok")
    OK(CacheStatus.OK),
    @JsonProperty("fail")
    FAIL(CacheStatus.FAIL),
    @JsonProperty("not-found")
    NOT_FOUND(CacheStatus.NOT_FOUND),
    @JsonProperty("none")
    NONE(CacheStatus.NONE);

    companion object {
        fun of(base: CacheStatus) = when(base) {
            CacheStatus.NONE -> NONE
            CacheStatus.NOT_FOUND -> NOT_FOUND
            CacheStatus.FAIL -> FAIL
            CacheStatus.OK -> OK
        }

    }
}
