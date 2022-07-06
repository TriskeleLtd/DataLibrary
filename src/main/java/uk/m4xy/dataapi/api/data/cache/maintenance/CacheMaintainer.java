package uk.m4xy.dataapi.api.data.cache.maintenance;

import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.cache.entry.CacheEntry;
import uk.m4xy.dataapi.impl.data.cache.maintainer.PurgeExpiredCacheMaintainer;

import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public interface CacheMaintainer {
    CacheMaintainer PURGE_EXPIRED = new PurgeExpiredCacheMaintainer();

    static <T extends DataType<T, K, D>, K, D extends Data<T, K, D>> void purgeExpired(Map<?, ? extends CacheEntry<T, K, D>> map, ReentrantReadWriteLock lock) {
        PURGE_EXPIRED.maintain(map, lock);
    }

    void maintain(Map<?, ? extends CacheEntry<?, ?, ?>> map, ReentrantReadWriteLock lock);


}
