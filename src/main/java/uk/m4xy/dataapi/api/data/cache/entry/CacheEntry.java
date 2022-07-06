package uk.m4xy.dataapi.api.data.cache.entry;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.cache.CacheExpiry;
import uk.m4xy.dataapi.api.data.cache.exception.CacheExpiredException;

public interface CacheEntry<T extends DataType<T, K, D>, K, D extends Data<T, K, D>> {

    @NotNull D get() throws CacheExpiredException;

    default boolean isExpired(CacheExpiry cacheExpiry) {
        return cacheExpiry.isExpired(this);
    }

    boolean isExpired();

    @NotNull K getKey();

    long getId();
    long getTimeChanged();

    void update(D newData);

    void expire();

    default boolean ignoresRemove() { return false; }

}
