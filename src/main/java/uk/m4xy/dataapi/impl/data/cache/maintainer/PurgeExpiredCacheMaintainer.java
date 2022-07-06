package uk.m4xy.dataapi.impl.data.cache.maintainer;

import uk.m4xy.dataapi.api.data.cache.entry.CacheEntry;
import uk.m4xy.dataapi.api.data.cache.maintenance.CacheMaintainer;

import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PurgeExpiredCacheMaintainer implements CacheMaintainer {
    public void maintain(Map<?, ? extends CacheEntry<?, ?, ?>> map, ReentrantReadWriteLock lock) {
        lock.writeLock().lock();

        try {
            map.entrySet().removeIf(entry -> !entry.getValue().ignoresRemove() && entry.getValue().isExpired());
        } finally {
            lock.writeLock().unlock();
        }
    }

}
