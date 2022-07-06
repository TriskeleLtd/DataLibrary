package uk.m4xy.dataapi.api.data.tree;

import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;

import java.util.Arrays;
import java.util.List;

/**
 * The idea of a data tree is to have multiple levels of an object being loaded.
 */
public class DataTree<T extends DataType<T, K, D>, K, D extends Data<T, K, D>> {

    private final DataType<T, K, D> dataType;
    private final List<DataSubType<T, K, D>> subTypeTree;

    @SafeVarargs
    public DataTree(DataType<T, K, D> dataType, DataSubType<T, K, D>... types) {
        this.dataType = dataType;
        this.subTypeTree = Arrays.asList(types);
    }

    public DataType<T, K, D> getDataType() {
        return dataType;
    }

    public List<DataSubType<T, K, D>> getSubTypes() {
        return this.subTypeTree;
    }


}
