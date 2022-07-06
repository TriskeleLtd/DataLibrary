package uk.m4xy.dataapi.api.data.tree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.persist.loader.DataLoader;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.cache.DataCache;
import uk.m4xy.dataapi.api.data.cache.DataCacher;
import uk.m4xy.dataapi.api.data.cache.exception.CacheNotFoundException;
import uk.m4xy.dataapi.api.util.EventSupplier;

public interface DataSubType<T extends DataType<T, K, D>, K, D extends Data<T, K, D>> {

    @NotNull
    static <T extends DataType<T, K, D>, K, D extends Data<T, K, D>> DataCacher<T, K, D> createSubTypeCacher(DataSubType<T, K, D> dataSubType) throws CacheNotFoundException {
        final DataCache<T, K, D> dataCache = dataSubType.getDataCache();

        if (dataCache == null) throw new CacheNotFoundException();

        return new DataCacher<>(
                dataSubType.getKeyLoadEventSupplier(),
                dataSubType.getIdLoadEventSupplier(),
                dataSubType.getDataLoader(),
                dataSubType.getDataCache()
        );
    }

    @NotNull DataType<T, K, D> getParentType();

    @NotNull DataLoader<T, K, D> getDataLoader();

    @Nullable DataCacher<T, K, D> getDataCacher();

    @Nullable EventSupplier<K> getKeyLoadEventSupplier();

    @Nullable EventSupplier<Long> getIdLoadEventSupplier();

    @Nullable
    default DataCache<T, K, D> getDataCache() {
        return this.getParentType().getDataCache();
    }


}
