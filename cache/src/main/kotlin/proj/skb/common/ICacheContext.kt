package proj.skb.common

interface ICacheContext {
    val id: String
    val obj: CacheObject
    val status: CacheStatus
    val errorMessage: String
    val stub: String
    fun isOk() = status == CacheStatus.OK
}
