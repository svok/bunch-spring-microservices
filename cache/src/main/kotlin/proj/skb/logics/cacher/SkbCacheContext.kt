package proj.skb.logics.cacher

import proj.skb.common.CacheObject
import proj.skb.common.CacheStatus
import proj.skb.common.ICacheContext

data class SkbCacheContext(
    override var id: String = "",
    override var obj: CacheObject = CacheObject.NONE,
    override var status: CacheStatus = CacheStatus.DEFAULT,
    override var errorMessage: String = "",
    override var stub: String = ""
): ICacheContext {
}
