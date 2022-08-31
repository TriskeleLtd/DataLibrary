package uk.m4xy.dataapi.api.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.cache.DataCache;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;
import uk.m4xy.dataapi.api.data.function.DataPrinter;
import uk.m4xy.dataapi.api.data.persist.DataLoader;
import uk.m4xy.dataapi.api.data.persist.DataSaver;
import uk.m4xy.dataapi.api.data.tree.DataTree;

public interface DataType<T extends DataType<T, K, D>, K, D extends Data<T, K, D>> {

    @NotNull K getKey(@NotNull D data) throws DataNotLoadedException;

    long getId(@NotNull D data) throws DataNotLoadedException;

    default @Nullable DataPrinter<T, D> getDataPrinter() {
        return null;
    }

    @Nullable DataCache<T, K, D> getDataCache();

    @Nullable DataLoader<T, K, D> getDataLoader();

    @Nullable DataSaver<T, K, D> getDataSaver();

    default @Nullable DataTree<T, K, D> getDataTree() {
        return null;
    }

}
