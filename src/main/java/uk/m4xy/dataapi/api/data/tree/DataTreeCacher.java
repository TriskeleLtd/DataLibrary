package uk.m4xy.dataapi.api.data.tree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.cache.DataCacher;

import java.util.HashMap;
import java.util.Map;

public class DataTreeCacher<T extends DataType<T, K, D>, K, D extends Data<T, K, D>> {
    private final DataTree<T, K, D> dataTree;
    private final Map<DataSubType<T, K, D>, DataCacher<T, K, D>> subTypeDataCachers;

    public DataTreeCacher(DataTree<T, K, D> dataTree) {
        this.dataTree = dataTree;
        this.subTypeDataCachers = new HashMap<>();

        this.dataTree.getSubTypes()
                .forEach(d -> this.subTypeDataCachers.put(d, DataSubType.createSubTypeCacher(d)));
    }

    @NotNull
    public DataTree<T, K, D> getDataTree() {
        return this.dataTree;
    }

    @Nullable
    public DataCacher<T, K, D> getDataCacher(DataSubType<T, K, D> dataSubType) {
        return this.subTypeDataCachers.get(dataSubType);
    }
}
