package uk.m4xy.dataapi.api.data.cache.entry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.persist.loader.DataLoader;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.cache.CacheExpiry;
import uk.m4xy.dataapi.api.data.cache.exception.CacheExpiredException;

import java.util.function.Supplier;

public class SelfLoadingCacheEntry<T extends DataType<T, K, D>, K, D extends Data<T, K, D>> extends PlainCacheEntry<T, K, D> {
    @NotNull
    private final Supplier<D> dataSupplier;

    public SelfLoadingCacheEntry(@NotNull CacheExpiry cacheExpiry, @NotNull K key, @NotNull DataLoader<T, K, D> dataLoader) {
        this(cacheExpiry, () -> dataLoader.loadData(key));
    }

    public SelfLoadingCacheEntry(@NotNull CacheExpiry cacheExpiry, long id, @NotNull DataLoader<T, K, D> dataLoader) {
        this(cacheExpiry, () -> dataLoader.loadDataFromId(id));
    }

    public SelfLoadingCacheEntry(@NotNull CacheExpiry cacheExpiry, long id, @NotNull K key, @NotNull DataLoader<T, K, D> dataLoader) {
        this(cacheExpiry, id, key, () -> dataLoader.loadDataFromId(id), null);
    }

    public SelfLoadingCacheEntry(@NotNull CacheExpiry cacheExpiry, @NotNull Supplier<D> dataSupplier) {
        this(cacheExpiry, dataSupplier, dataSupplier.get());
    }

    public SelfLoadingCacheEntry(@NotNull CacheExpiry cacheExpiry, @NotNull Supplier<D> dataSupplier, @NotNull D data) {
        this(cacheExpiry, data.getId(), data.getKey(), dataSupplier, data);
    }

    public SelfLoadingCacheEntry(@NotNull CacheExpiry cacheExpiry,
                                 long id,
                                 @NotNull K key,
                                 @NotNull Supplier<D> dataSupplier,
                                 @Nullable D data) {
        super(cacheExpiry, id, key, data);

        this.dataSupplier = dataSupplier;
    }

    @Override
    public @NotNull D get() {
        try {
            return super.get();
        } catch (CacheExpiredException e) {
            this.fetchData();
            return this.get();
        }
    }

    private void fetchData() {
        this.update(this.dataSupplier.get());
    }


    @Override
    public boolean ignoresRemove() {
        return true;
    }
}
