package uk.m4xy.dataapi.impl.data.sql.loader;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.element.type.DataField;
import uk.m4xy.dataapi.api.data.persist.DataLoader;
import uk.m4xy.dataapi.impl.data.sql.SQLPointer;

public interface SQLDataLoader<T extends DataType<T, K, D>, K, D extends Data<T, K, D>> extends DataLoader<T, K, D> {

    <E> DataField<E> loadData(@NotNull SQLPointer sqlPointer);

}
