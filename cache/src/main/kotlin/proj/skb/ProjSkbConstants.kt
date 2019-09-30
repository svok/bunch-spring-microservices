package proj.skb

object ProjSkbConstants {
    const val API_VERSION = "1.0"
    const val DEFAULT_DELAY = "PT120S" // Default delay is 20 seconds
    const val DEFAULT_MAX_DELAY = "PT240S"
    const val DEFAULT_MIN_DELAY = "PT0S"

    /**
     * Список имен очередй, для которых разрешен доступ по умолчанию. Перекрывается из файла настроек
     */
    val DEFAULT_ALLOWED_QUEUES: List<String> = listOf("test")

    const val DEFAULT_IGNITE_CACHE_NAME = "ignite-skb-cache"

    const val STUB_GET_OK = "ok"
    const val STUB_GET_NOT_FOUND = "not-found"
    const val STUB_GET_FAIL = "fail"
}
