package uk.m4xy.dataapi.api.data;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.cache.DataCache;
import uk.m4xy.dataapi.api.data.function.DataPrinter;
import uk.m4xy.dataapi.api.data.persist.DataLoader;
import uk.m4xy.dataapi.api.data.persist.DataSaver;
import uk.m4xy.dataapi.api.data.tree.DataTree;

public interface DataTypeInitializer<T extends DataType<T, K, D>, K, D extends Data<T, K, D>> {


    Class<K> getKeyType();

    Class<D> getDataClass();

    Class<T> getTypeClass();

    void setDataCache(@NotNull DataCache<T, K, D> dataCache);

    void setDataLoader(@NotNull DataLoader<T, K, D> dataLoader);

    default void setDataSaver(@NotNull DataSaver<T, K, D> dataSaver) {
        // Do Nothing //
    }

    default void setDataPrinter(@NotNull DataPrinter<T, D> dataPrinter) {
        // Do Nothing //
    }

    default void setDataTree(DataTree<T, K, D> dataTree) {
        // Do Nothing //
    }

}
