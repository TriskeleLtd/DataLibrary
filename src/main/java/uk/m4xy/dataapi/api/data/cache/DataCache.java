package uk.m4xy.dataapi.api.data.cache;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.persist.DataLoader;
import uk.m4xy.dataapi.api.data.cache.entry.CacheEntry;
import uk.m4xy.dataapi.api.data.cache.entry.PlainCacheEntry;
import uk.m4xy.dataapi.api.data.cache.entry.SelfLoadingCacheEntry;
import uk.m4xy.dataapi.api.data.cache.exception.CacheExpiredException;
import uk.m4xy.dataapi.api.data.cache.exception.KeyNotInCacheException;
import uk.m4xy.dataapi.api.util.maintenance.Maintainable;

import java.util.function.Supplier;

public interface DataCache<T extends DataType<T, K, D>, K, D extends Data<T, K, D>> extends Maintainable {

    default void cache(long id, @NotNull DataLoader<T, K, D> dataLoader) {
        this.cache(new SelfLoadingCacheEntry<>(this.getCacheExpiry(), id, dataLoader));
    }

    default void cache(@NotNull K key, @NotNull DataLoader<T, K, D> dataLoader) {
        this.cache(new SelfLoadingCacheEntry<>(this.getCacheExpiry(), key, dataLoader));
    }

    default void cache(@NotNull Supplier<D> dataSupplier) {
        this.cache(new SelfLoadingCacheEntry<>(this.getCacheExpiry(), dataSupplier));
    }

    default void cache(@NotNull D data, @NotNull Supplier<D> dataSupplier) {
        this.cache(new SelfLoadingCacheEntry<>(this.getCacheExpiry(), dataSupplier, data));
    }
    default void cache(@NotNull D data) {
        this.cache(new PlainCacheEntry<>(this.getCacheExpiry(), data));
    }

    void cache(@NotNull CacheEntry<T, K, D> cacheEntry);

    @Nullable D get(@NotNull K key) throws CacheExpiredException, KeyNotInCacheException;

    @Nullable D get(long id) throws CacheExpiredException, KeyNotInCacheException;

    void expire(@NotNull K key);

    void expire(long id);

    default void expire(@NotNull D data) {
        this.expire(data.getId());
    }

    @NotNull CacheExpiry getCacheExpiry();

}
