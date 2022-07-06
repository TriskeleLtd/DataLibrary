package uk.m4xy.dataapi.api.data.cache;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.persist.loader.DataLoader;
import uk.m4xy.dataapi.api.util.EventSupplier;

import java.util.function.Consumer;

public class DataCacher<T extends DataType<T, K, D>, K, D extends Data<T, K, D>> {
    @NotNull DataLoader<T, K, D> dataLoader;
    @NotNull DataCache<T, K, D> dataCache;
    @Nullable EventSupplier<K> keyEventSupplier;
    @Nullable EventSupplier<Long> idEventSupplier;


    public DataCacher(@Nullable EventSupplier<K> keyEventSupplier,
                      @Nullable EventSupplier<Long> idEventSupplier,
                      @NotNull DataLoader<T, K, D> dataLoader,
                      @NotNull DataCache<T, K, D> dataCache) {
        this.dataLoader = dataLoader;
        this.dataCache = dataCache;

        this.keyEventSupplier = keyEventSupplier;
        this.idEventSupplier = idEventSupplier;
    }

    private static <T extends DataType<T, K, D>, K, D extends Data<T, K, D>> Consumer<K> getCacheFunction(DataLoader<T, K, D> loader, DataCache<T, K, D> cache) {
        return x -> {
            final D data = loader.loadData(x);
            if (data != null)
                cache.cache(data);
        };
    }

    private static <T extends DataType<T, K, D>, K, D extends Data<T, K, D>> Consumer<Long> getIdCacheFunction(DataLoader<T, K, D> loader, DataCache<T, K, D> cache) {
        return x -> {
            final D data = loader.loadDataFromId(x);
            if (data != null) {
                cache.cache(data);
            }
        };
    }

    private void registerListeners() {
        if (this.keyEventSupplier != null) {
            this.keyEventSupplier.setListener(getCacheFunction(this.dataLoader, this.dataCache));
        }

        if (this.idEventSupplier != null) {
            this.idEventSupplier.setListener(getIdCacheFunction(this.dataLoader, this.dataCache));
        }
    }

}
