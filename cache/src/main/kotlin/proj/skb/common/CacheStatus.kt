package proj.skb.common

enum class CacheStatus {
    NONE,
    OK,
    NOT_FOUND,
    FAIL;
    companion object {
        val DEFAULT = NONE
    }
}
