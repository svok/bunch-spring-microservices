package proj.skb.logics.router

import org.apache.ignite.Ignite
import org.apache.ignite.events.CacheEvent
import org.apache.ignite.events.EventType
import org.apache.ignite.lang.IgnitePredicate
import proj.skb.common.CacheObject
import java.util.*
import java.util.concurrent.locks.ReentrantReadWriteLock
import javax.cache.Cache
import kotlin.concurrent.write

/**
 * This class hooks all events of put and remove for Ignite cache and creates timers for all of the cached objects.
 * As the delay is timed outed the handler is invoked
 */
class CacheWatcher(
    val ignite: Ignite,
    val cache: Cache<String, CacheObject>,
    val handler: (key: String, value: CacheObject) -> Unit = { _, _ -> }
) {
    private val lock = ReentrantReadWriteLock()

    fun init() {
        subscribe()
        handleOld()
    }

    private fun subscribe() {
        // Subscribe to specified cache events occuring on local node.
        ignite.events().localListen(
            LocalListen(this),
            EventType.EVT_CACHE_OBJECT_PUT,
            EventType.EVT_CACHE_OBJECT_REMOVED
        )
    }

    private fun handleOld() {
        cache.forEach {
            addTimer(it.key, it.value)
        }
    }

    private fun addTimer(key: String, value: CacheObject?) = lock.write {
        val timer = Timer()

    }

    private fun delTimer(key: String, value: CacheObject?) = lock.write {

    }


    private class LocalListen(val watcher: CacheWatcher): IgnitePredicate<CacheEvent> {
        override fun apply(e: CacheEvent?): Boolean {
            if (e == null) return true
            when(e.name()) {
                EventType.EVT_CACHE_OBJECT_PUT.toString() -> watcher.addTimer(e.key<String>(), e.newValue() as? CacheObject)
                EventType.EVT_CACHE_OBJECT_REMOVED.toString() -> watcher.addTimer(e.key<String>(), e.oldValue() as? CacheObject)
            }
            return true
        }

    }

}
